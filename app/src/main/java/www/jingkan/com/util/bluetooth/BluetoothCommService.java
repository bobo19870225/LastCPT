/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.util.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import www.jingkan.com.util.CallbackMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

import static www.jingkan.com.util.SystemConstant.SPP_UUID;

@Singleton
public class BluetoothCommService {
    private static final String TAG = "BluetoothComm";
    private static final boolean D = true;
    private static final String NAME = "BluetoothComm";
    // Member fields
    private final BluetoothAdapter mAdapter;
    //    private final Handler mHandler;
    @Inject
    CallbackMessage callbackMessage;
    private final MutableLiveData<CallbackMessage> bluetoothMessageMutableLiveData = new MutableLiveData<>();
    private int mState;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0; // do nothing
    public static final int STATE_LISTEN = 1; // 监听连接
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing
    // connection
    public static final int STATE_CONNECTED = 3;   // 已连接上远程设备
    public static final int STATE_CONNECT_FAILED = 4; // now initiating an outgoing
    public static final int STATE_CONNECT_LOST = 5; // now initiating an outgoing
    //handler发生信息编号
    public final static int MESSAGE_WRITE = 0;
    public final static int MESSAGE_READ = 1;
    public final static int MESSAGE_TOAST = 2;
    public final static int MESSAGE_DEVICE_NAME = 3;
    public final static int MESSAGE_STATE_CHANGE = 4;
    public static String DEVICE_NAME = "device_name";
    public static String TOAST = "toast";

    @Inject
    public BluetoothCommService() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
    }

    public MutableLiveData<CallbackMessage> getBluetoothMessageMutableLiveData() {
        return bluetoothMessageMutableLiveData;
    }

    /**
     * Set the current state of the chat connection
     *
     * @param state An integer defining the current connection state
     */
    private synchronized void setState(int state) {
        if (D)
            Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
        // Give the new state to the Handler so the UI Activity can update
//        mHandler.obtainMessage(MESSAGE_STATE_CHANGE, state, -1)
//                .sendToTarget();

        callbackMessage.setValue(MESSAGE_STATE_CHANGE, state, -1);
        bluetoothMessageMutableLiveData.postValue(callbackMessage);

    }

    /**
     * Return the current connection state.
     */
    public synchronized int getState() {
        return mState;
    }

    /**
     * Start the service. Specifically start AcceptThread to begin a session in
     * listening (server) mode. Called by the Activity onResume() 开启监听线程
     */
    public synchronized void start() {
        if (D)
            Log.d(TAG, "start");
        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        // Start the thread to listen on a BluetoothServerSocket
        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();// 开启监听线程
        }
        setState(STATE_LISTEN);
    }

    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     *
     * @param device The BluetoothDevice to connect
     */
    public synchronized void connect(BluetoothDevice device) {
        if (D)
            Log.d(TAG, "connect to: " + device);
        if (mState == STATE_CONNECTED) {//已经连接了
            setState(STATE_CONNECTED);//通知界面
            return;
        }
        stop();
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();// 开启新的连接请求线程
        setState(STATE_CONNECTING);
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     *
     * @param socket The BluetoothSocket on which the connection was made
     * @param device The BluetoothDevice that has been connected
     */
    private synchronized void connected(BluetoothSocket socket,
                                        BluetoothDevice device) {
        if (D)
            Log.d(TAG, "connected");
        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        // Cancel the accept thread because we only want to connect to one
        // device
        if (mAcceptThread != null) {
            mAcceptThread.cancel();
            mAcceptThread = null;
        }
        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();// 和客户端开始通信
        // Send the name of the connected device back to the UI Activity
//        Message msg = mHandler.obtainMessage(MESSAGE_DEVICE_NAME);
//        Bundle bundle = new Bundle();
//        bundle.putString(DEVICE_NAME, device.getName());
//        msg.setData(bundle);
//        mHandler.sendMessage(msg);
        callbackMessage.setValue(MESSAGE_STATE_CHANGE, STATE_CONNECTED, -1, device.getName());
        bluetoothMessageMutableLiveData.postValue(callbackMessage);
        setState(STATE_CONNECTED);

    }

    /**
     * Stop all threads
     */
    public synchronized void stop() {
        if (D)
            Log.d(TAG, "stop");
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (mAcceptThread != null) {
            mAcceptThread.cancel();
            mAcceptThread = null;
        }
        setState(STATE_NONE);
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED)
                return;
            r = mConnectedThread;// 得到连接线程
        }
        // Perform the write unsynchronized
        r.write(out);
    }

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        setState(STATE_LISTEN);
        // Send a failure message back to the Activity
//        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
//        Bundle bundle = new Bundle();
//        bundle.putString(TOAST, "无法连接设备");
//        msg.setData(bundle);
//        mHandler.sendMessage(msg);
//        callbackMessage.setValue(MESSAGE_STATE_CHANGE, STATE_CONNECT_FAILED, -1, "无法连接设备");
        bluetoothMessageMutableLiveData.postValue(callbackMessage);

        setState(STATE_CONNECT_FAILED);
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
        setState(STATE_LISTEN);
        // Send a failure message back to the Activity
//        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
//        Bundle bundle = new Bundle();
//        bundle.putString(TOAST, "失去设备的连接");
//        msg.setData(bundle);
//        mHandler.sendMessage(msg);
//        callbackMessage.setValue(MESSAGE_STATE_CHANGE, STATE_CONNECT_LOST, -1, "失去设备的连接");
        bluetoothMessageMutableLiveData.postValue(callbackMessage);

        setState(STATE_CONNECT_LOST);
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted (or
     * until cancelled).
     */
    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;

        private AcceptThread() {
            BluetoothServerSocket tmp = null;
            // Create a new listening server socket
            try {
                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME,
                        SPP_UUID);
            } catch (IOException e) {
                Log.e(TAG, "listen() failed", e);
            }
            // 得到BluetoothServerSocket对象
            mmServerSocket = tmp;
        }

        @Override
        public void run() {
            if (D)
                Log.d(TAG, "BEGIN mAcceptThread" + this);
            setName("AcceptThread");// set the name of thread
            BluetoothSocket socket;// 建立一个服务器
            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {// 若没有连接，一直执行
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    // 是一个阻塞线程，因此不要放在Activity中
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "accept() failed", e);
                    break;
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized (BluetoothCommService.this) {
                        switch (mState) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                // Situation normal. Start the connected thread.
                                connected(socket, socket.getRemoteDevice());
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                // Either not ready or already connected. Terminate
                                // new socket.
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    Log.e(TAG, "Could not close unwanted socket", e);
                                }
                                break;
                        }
                    }
                }
            }
            if (D)
                Log.i(TAG, "END mAcceptThread");
        }

        private void cancel() {
            if (D)
                Log.d(TAG, "cancel " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of server failed", e);
            }
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection with a
     * device. It runs straight through; the connection either succeeds or
     * fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;// 远程设备

        private ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                // 获得一个BluetoothSocket对象
                tmp = device.createRfcommSocketToServiceRecord(SPP_UUID);
            } catch (IOException e) {
                Log.e(TAG, "create() failed", e);
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");

            // 取消搜索
            mAdapter.cancelDiscovery();
            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                connectionFailed();
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG,
                            "unable to close() socket during connection failure",
                            e2);
                }
                // Start the service over to restart listening mode
                BluetoothCommService.this.start();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothCommService.this) {
                mConnectThread = null;
            }

            // Start the connected thread，已连接上，管理连接
            connected(mmSocket, mmDevice);
        }

        private void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device. It handles all
     * incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        private ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        @Override
        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            // int bytes;
            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    int i = 0;
                    byte[] b = new byte[128];
                    byte b1;
                    do {
                        b1 = (byte) mmInStream.read();
                        b[i] = b1;
                        i++;
                    } while (b1 != '\n' && b1 != -1 && b1 != '\r');
                    if (b[0] != '\r' && b[0] != '\n') {
//                        mHandler.obtainMessage(MESSAGE_READ, i, -1, b)
//                                .sendToTarget();
                        callbackMessage.setValue(MESSAGE_READ, i, -1, b);
                        bluetoothMessageMutableLiveData.postValue(callbackMessage);
                    }

                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();
                    break;
                }
            }
//            byte b1;
//            byte[] b = new byte[128];
//            int i = 0;
//            do {
//                try {
//                    b1 = (byte) mmInStream.read();//阻塞
//                } catch (IOException e) {
//                    Log.e(TAG, "disconnected", e);
//                    connectionLost();
//                    break;
//                }
//
//                while (b1 != '\r' && b1 != '\n') {
//
//                    if (b1 == 32) {
//                        if (i < b.length - 1) {
//                            b[i] = b1;
//                            i++;
//                        }
//                    } else if (b1 > 44 && b1 < 59) {
//                        if (i < b.length - 1) {
//                            b[i] = b1;
//                            i++;
//                        }
//                    } else if (b1 > 64 && b1 < 91) {
//                        if (i < b.length - 1) {
//                            b[i] = b1;
//                            i++;
//                        }
//                    } else if (b1 > 96 && b1 < 123) {
//                        if (i < b.length - 1) {
//                            b[i] = b1;
//                            i++;
//                        }
//                    }
//
//                }
//                if (i != 0) {
//                    b[i] = '\r';//加结束标志
//                    mHandler.obtainMessage(MESSAGE_READ, i, -1, b).sendToTarget();
//                    i = 0;
//                }
//
//            } while (true);

        }

        /**
         * Write to the connected OutStream.
         *
         * @param buffer The bytes to write
         */
        void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);
                mmOutStream.flush();
                // Share the sent message back to the UI Activity
//                mHandler.obtainMessage(MESSAGE_WRITE, -1, -1,
//                        buffer).sendToTarget();
                callbackMessage.setValue(MESSAGE_WRITE, -1, -1, buffer);
                bluetoothMessageMutableLiveData.postValue(callbackMessage);

            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        private void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}

