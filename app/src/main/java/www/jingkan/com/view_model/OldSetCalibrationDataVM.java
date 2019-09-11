package www.jingkan.com.view_model;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;

import www.jingkan.com.db.dao.CalibrationProbeDao;
import www.jingkan.com.db.dao.MemoryDataDao;
import www.jingkan.com.util.SystemConstant;
import www.jingkan.com.util.VibratorUtil;
import www.jingkan.com.util.bluetooth.BluetoothCommService;
import www.jingkan.com.util.bluetooth.BluetoothUtil;

/**
 * Created by Sampson on 2019-09-06.
 * LastCPT 2
 * {@link www.jingkan.com.view.OldSetCalibrationDataActivity}
 */
public class OldSetCalibrationDataVM extends SetCalibrationDataVM {
    private int obliquityX = 0;
    private int obliquityY = 0;
    private int obliquityZ = 0;

    public OldSetCalibrationDataVM(@NonNull Application application, BluetoothUtil bluetoothUtil, BluetoothCommService bluetoothCommService, MemoryDataDao memoryDataDao, CalibrationProbeDao calibrationProbeDao, VibratorUtil vibratorUtil) {
        super(application, bluetoothUtil, bluetoothCommService, memoryDataDao, calibrationProbeDao, vibratorUtil);
        command = new byte[281];
    }

    @Override
    protected void sendData() {
        String sn = ldSN.getValue();
        String area = ldArea.getValue();
        String number = ldNumber.getValue();
        Acc[0][1][7] = Acc[0][0][7] / Acc[0][0][6] * Acc[0][1][6];
        Acc[0][2][7] = Acc[0][0][7] / Acc[0][0][6] * Acc[0][2][6];
        Acc[1][1][7] = Acc[1][0][7] / Acc[1][0][6] * Acc[1][1][6];
        Acc[1][2][7] = Acc[1][0][7] / Acc[1][0][6] * Acc[1][2][6];
        command[0] = 83;
        command[1] = 69;
        command[2] = 84;
        command[3] = 85;
        command[4] = 80;
        if (sn != null && sn.length() != 0) {
            sn = sn + "        ";
            sn = sn.substring(0, 8);
            switch (strModel) {
                case SystemConstant.SINGLE_BRIDGE_3:
                    if (area != null) {
                        if (area.equals("10")) {
                            snToW = sn + "C";
                        } else {
                            snToW = sn + "I";
                        }
                    }
                    break;
                case SystemConstant.SINGLE_BRIDGE_4:
                    if (area != null) {
                        if (area.equals("10")) {
                            snToW = sn + "D";
                        } else {
                            snToW = sn + "J";
                        }
                    }
                    break;
                case SystemConstant.SINGLE_BRIDGE_6:
                    if (area != null) {
                        if (area.equals("10")) {
                            snToW = sn + "F";
                        } else {
                            snToW = sn + "L";
                        }
                    }
                    break;
                case SystemConstant.DOUBLE_BRIDGE_3:
                    if (area != null) {
                        if (area.equals("10")) {
                            snToW = sn + "O";
                        } else {
                            snToW = sn + "U";
                        }
                    }
                    break;
                case SystemConstant.DOUBLE_BRIDGE_4:
                    if (area != null) {
                        if (area.equals("10")) {
                            snToW = sn + "P";
                        } else {
                            snToW = sn + "V";
                        }
                    }
                    break;
                case SystemConstant.DOUBLE_BRIDGE_6:
                    if (area != null) {
                        if (area.equals("10")) {
                            snToW = sn + "R";
                        } else {
                            snToW = sn + "X";
                        }
                    }
                    break;
                case SystemConstant.VANE:
                    if (area != null) {
                        if (area.equals("10")) {
                            snToW = sn + "Y";
                        } else {
                            snToW = sn + "Z";
                        }
                    }
                    break;

                default:
                    break;
            }

            if (number != null) {
                String[] split = number.split("-");
                if (split[2] != null) {
                    snToW = snToW + split[2];
                }

                char[] bm = snToW.toCharArray();
                for (int i = 1; i < 13; i++) {
                    command[i + 4] = (byte) bm[i - 1];
                }
                snToW = null;
            }


            if (obliquityX < 0) {
                obliquityX = 65536 + obliquityX;
            }
            if (obliquityY < 0) {
                obliquityY = 65536 + obliquityY;
            }
            if (obliquityZ < 0) {
                obliquityZ = 65536 + obliquityZ;
            }
            if (Acc[0][1][6] >= 0) {
                Acc[0][1][0] = 1;
            } else {
                Acc[0][1][0] = -1;
            }
            if (Acc[1][1][6] >= 0) {
                Acc[1][1][0] = 1;
            } else {
                Acc[1][1][0] = -1;
            }

            command[17] = (byte) (obliquityX / 256);
            command[18] = (byte) (obliquityX % 256);
            command[21] = (byte) (obliquityY / 256);
            command[22] = (byte) (obliquityY % 256);
            command[19] = (byte) (obliquityZ / 256);
            command[20] = (byte) (obliquityZ % 256);
            for (int i = 0; i < 8; i++) {
                command[i * 4 + 151] = (byte) (Acc[0][0][i] / 16777216);
                command[i * 4 + 152] = (byte) ((Acc[0][0][i] % 16777216) / 65536);
                command[i * 4 + 153] = (byte) ((Acc[0][0][i] % 65536) / 256);
                command[i * 4 + 154] = (byte) (Acc[0][0][i] % 256);

                command[i * 4 + 183] = (byte) (Acc[0][0][i] / 16777216);
                command[i * 4 + 184] = (byte) ((Acc[0][0][i] % 16777216) / 65536);
                command[i * 4 + 185] = (byte) ((Acc[0][0][i] % 65536) / 256);
                command[i * 4 + 186] = (byte) (Acc[0][0][i] % 256);

                command[i * 4 + 215] = (byte) (Acc[1][0][i] / 16777216);
                command[i * 4 + 216] = (byte) ((Acc[1][0][i] % 16777216) / 65536);
                command[i * 4 + 217] = (byte) ((Acc[1][0][i] % 65536) / 256);
                command[i * 4 + 218] = (byte) (Acc[1][0][i] % 256);

                command[i * 4 + 247] = (byte) (Acc[1][0][i] / 16777216);
                command[i * 4 + 248] = (byte) ((Acc[1][0][i] % 16777216) / 65536);
                command[i * 4 + 249] = (byte) ((Acc[1][0][i] % 65536) / 256);
                command[i * 4 + 250] = (byte) (Acc[1][0][i] % 256);

                command[i * 4 + 23] = (byte) ((Math.abs(Acc[0][1][i]) / 0.0023283) / 16777216);
                command[i * 4 + 24] = (byte) (((Math.abs(Acc[0][1][i]) / 0.0023283) % 16777216) / 65536);
                command[i * 4 + 25] = (byte) (((Math.abs(Acc[0][1][i]) / 0.0023283) % 65536) / 256);
                command[i * 4 + 26] = (byte) ((Math.abs(Acc[0][1][i]) / 0.0023283) % 256);

                command[i * 4 + 55] = (byte) ((Math.abs(Acc[0][2][i]) / 0.0023283) / 16777216);
                command[i * 4 + 56] = (byte) (((Math.abs(Acc[0][2][i]) / 0.0023283) % 16777216) / 65536);
                command[i * 4 + 57] = (byte) (((Math.abs(Acc[0][2][i]) / 0.0023283) % 65536) / 256);
                command[i * 4 + 58] = (byte) ((Math.abs(Acc[0][2][i]) / 0.0023283) % 256);

                command[i * 4 + 87] = (byte) ((Math.abs(Acc[1][1][i]) / 0.0023283) / 16777216);
                command[i * 4 + 88] = (byte) (((Math.abs(Acc[1][1][i]) / 0.0023283) % 16777216) / 65536);
                command[i * 4 + 89] = (byte) (((Math.abs(Acc[1][1][i]) / 0.0023283) % 65536) / 256);
                command[i * 4 + 90] = (byte) ((Math.abs(Acc[1][1][i]) / 0.0023283) % 256);

                command[i * 4 + 119] = (byte) ((Math.abs(Acc[1][2][i]) / 0.0023283) / 16777216);
                command[i * 4 + 120] = (byte) (((Math.abs(Acc[1][2][i]) / 0.0023283) % 16777216) / 65536);
                command[i * 4 + 121] = (byte) (((Math.abs(Acc[1][2][i]) / 0.0023283) % 65536) / 256);
                command[i * 4 + 122] = (byte) ((Math.abs(Acc[1][2][i]) / 0.0023283) % 256);
            }
            if (Acc[0][1][6] >= 0) {
                command[279] = 0x1;
            } else {
                command[279] = 0x10;
            }
            if (Acc[1][1][6] >= 0) {
                command[280] = 0x67;
            } else {
                command[280] = 0x76;
            }
            ds = 0;
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 你要做的事。。。
                    sendMessage(command);
                    ds++;
                    if (ds == 9) {
                        timer.cancel();// 取消操作
                        toast("重置成功");
                    }
                }
            }, 0, 1000);// 0秒后执行，每1秒执行一次

        }
    }
}
