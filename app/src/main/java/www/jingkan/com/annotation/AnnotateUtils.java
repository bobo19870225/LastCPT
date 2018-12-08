/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.annotation;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import www.jingkan.com.base.BaseActivity;
import www.jingkan.com.framework.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by laidayuan on 16/1/8.
 */

@CoderClassDesc(author="laidayuan", desc="注解解析的工具类", date="2016-1-8")
public class AnnotateUtils {
	/**
	 * @param currentObj
	 *            当前类，一般为Activity或Fragment
	 * @param sourceView
	 *            待绑定控件的直接或间接父控件
	 */
	public static void initBindView(Object currentObj, View sourceView) {

		if (currentObj == null || sourceView == null) {
			return;
		}

		Class clazz = currentObj.getClass();
		while (clazz != null) {
			initBindClassView(currentObj, clazz, sourceView);

			// LogUtil.e("className = " + clazz.getName() +
			// "--"+ViewDelegate.class.getName());
			if (clazz.getName().equalsIgnoreCase(BaseActivity.class.getName())) {
				break;
			}

			clazz = clazz.getSuperclass();
		}

	}

	public static void hideAllEditText(Context context, Class clazzs,
                                       View sourceView) {

		if (context == null || sourceView == null || clazzs == null) {
			return;
		}

		Class clazz = clazzs;
		while (clazz != null) {
			hideAllEditTextEx(context, clazz, sourceView);

			if (clazz.getName().equalsIgnoreCase(BaseActivity.class.getName())) {
				break;
			}

			clazz = clazz.getSuperclass();
		}

	}

	public static void hideAllEditTextEx(Context context, Class clazz,
                                         View sourceView) {

		// 通过反射获取到全部属性，反射的字段可能是一个类（静态）字段或实例字段
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {

			for (Field field : fields) {

				// 返回BindView类型的注解内容
				BindView bindView = field.getAnnotation(BindView.class);
				if (bindView != null) {
					int viewId = bindView.id();
					Object view = sourceView.findViewById(viewId);
					if (view instanceof EditText) {
						EditText editText = (EditText) view;
						hideKeyboard(context, editText);
					}
				}
			}
		}

	}

	public static void hideKeyboard(Context context, EditText edit) {
		if (edit != null) {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
		}

	}

	public static void initBindClassView(Object currentObj, Class clazz,
                                         View sourceView) {
		// 通过反射获取到全部属性，反射的字段可能是一个类（静态）字段或实例字段
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				// 返回BindView类型的注解内容
				BindView bindView = field.getAnnotation(BindView.class);
				if (bindView != null) {
					int viewId = bindView.id();
					boolean clickLis = bindView.click();
					try {
						View view = sourceView.findViewById(viewId);
						if (view == null) {
							continue;
						}

						field.setAccessible(true);
						if (clickLis) {
							view.setOnClickListener((View.OnClickListener) currentObj);
						}
						// 将currentClass的field赋值为sourceView.findViewById(viewId)
						field.set(currentObj, view);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 必须在setContentView之后调用
	 * 
	 * @param aty
	 *            Activity对象
	 */
	public static void initBindView(Activity aty) {
		initBindView(aty, aty.getWindow().getDecorView());
	}

	/**
	 * 必须在setContentView之后调用
	 * 
	 * @param view
	 *            侵入式的view，例如使用inflater载入的view
	 */
	public static void initBindView(View view) {
		Context cxt = view.getContext();
		if (cxt instanceof Activity) {
			initBindView((Activity) cxt);
		} else {
			throw new RuntimeException("view must into Activity");
		}
	}

	/**
	 * 必须在setContentView之后调用
	 * 
	 * @param frag
	 *            要初始化的Fragment
	 */
	public static void initBindView(Fragment frag) {
		initBindView(frag, frag.getActivity().getWindow().getDecorView());
	}

	
	@CoderMethodDesc(author="laidayuan", desc="获取线程调用栈信息的函数", date="2016-1-8")
	public static ArrayList<Map<String, String>> getTheadStackTrace() {
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		StackTraceElement elements[] = Thread.currentThread().getStackTrace();
		if (elements != null) {
			for (StackTraceElement element : elements) {
				String method = element.getMethodName();
				String name = element.getClassName(); // 获取到类名
				//name = name.substring(name.lastIndexOf(".") + 1);
				if (name.contains("$")) {
					int index = name.indexOf("$");
					if (index != -1) {
						name = name.substring(0, index);
					}
				}
				
				Map<String, String> map = getCoderDesc(name, method, ""+element.getLineNumber());
				if (map != null) {
					list.add(map);
				}
			}
		}
		
		return list;
	}
	
	
	@CoderMethodDesc(author="laidayuan", desc="获取类和函数的代码信息", date="2016-1-8")
	public static Map<String, String> getCoderDesc(String name, String method, String line) {
		if (!StringUtils.isNotEmpty(name) || !StringUtils.isNotEmpty(method)) {
			return null;
		}

		Map<String, String> retMap = new HashMap<>();
		Class clazz;
		try {
			clazz = Class.forName(name);
			
			retMap.put("c_name", clazz.getName());
			retMap.put("m_line", ""+line);
			retMap.put("m_name", method);
			
			CoderClassDesc coderClass = (CoderClassDesc) clazz
					.getAnnotation(CoderClassDesc.class);
			if (coderClass != null) {
				
				if (coderClass.author() != null) {
					retMap.put("c_author", coderClass.author());
				}

				if (coderClass.desc() != null) {
					retMap.put("c_desc", coderClass.desc());
				}

				if (coderClass.date() != null) {
					retMap.put("c_date", coderClass.date());
				}
				
				Method clazzMethods[] = clazz.getDeclaredMethods();
				if (clazzMethods != null && clazzMethods.length > 0) {
					for (Method m : clazzMethods) {
						//LogUtil.i("name = " + m.getName());
						if (m != null && m.getName().equalsIgnoreCase(method)) {
							CoderMethodDesc coderMethod = m
									.getAnnotation(CoderMethodDesc.class);
							if (coderMethod != null) {
								if (coderMethod.author() != null) {
									retMap.put("m_author", coderMethod.author());
								}

								if (coderMethod.desc() != null) {
									retMap.put("m_desc", coderMethod.desc());
								}

								if (coderMethod.date() != null) {
									retMap.put("m_date", coderMethod.date());
								}
							}

							break;
						}
					}
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return retMap;
	}
	
	

}
