package com.alipay.pussycat.provider.model;

import java.util.List;

import com.alipay.pussycat.core.common.enums.PussycatExceptionEnum;
import com.alipay.pussycat.core.common.exception.PussycatException;
import com.alipay.pussycat.provider.utils.CheckManager;
import com.google.common.base.Preconditions;

/**
 * 
 * @author wb-smj330392
 * @create 2018-06-13 11:11
 */
public class ServiceWrapper extends CheckManager {

	/**** 服务实现类 ****/
	private Object target;
	/****** 服务名 *****/
	private String serviceName;
	/****** 服务接口下的方法编织 *****/
	private List<MethodWrapper> methodProvider;
	/****权重，默认是50****/
	private volatile int weight = 50;
	/****超时时间，默认是3000 ****/
	private volatile int timeout = 3000;
	/*****接口白名单 ****/
	private String whiteSheet;
	/*****版本号 ****/
	private String version;
	/*****是否异步返回 ****/
	private boolean async;


	/*****是否注册进注册中心　 ****/
	private boolean isSupportRegister;

	public ServiceWrapper(Object target, String serviceName,
                           int weight,boolean isSupportRegister,String whiteSheet,int timeout,String version,boolean async, List<MethodWrapper> methodProvider) {
		this.target = target;
		this.serviceName = serviceName;
		this.weight = weight;
		this.methodProvider = methodProvider;
		this.isSupportRegister = isSupportRegister;
		this.whiteSheet = whiteSheet;
		this.timeout = timeout;
		this.version = version;
		this.async = async;
		//init();
	}

	/**
	 * 初始化接口中的方法
	 * @return
	 */
	private List<MethodWrapper> init(){
		check();

		return null;
	}

	private void check() {
		Preconditions.checkNotNull(target);
		Preconditions.checkNotNull(serviceName);
		Preconditions.checkArgument(timeout > 0, "超时时间不能小于0");

		try {
			Class<?> serviceItf = Class.forName(serviceName);
			if (!serviceItf.isInterface()) {
				throw new PussycatException(PussycatExceptionEnum.E_10004);
			}
			if (!serviceItf.isAssignableFrom(target.getClass())) {
				throw new PussycatException(PussycatExceptionEnum.E_10005);
			}
		} catch (Exception e) {
		}

	}


	@Override
	public String toString() {
		return "ServiceWrapper [target=" + target + ",  serviceName="
				+ serviceName + ", weight=" + weight + "]";
	}

	public Object getTarget() {
		return target;
	}

	public String getServiceName() {
		return serviceName;
	}

	public List<MethodWrapper> getMethodProvider() {
		return methodProvider;
	}

	public int getWeight() {
		return weight;
	}

	public int getTimeout() {
		return timeout;
	}

	public String getWhiteSheet() {
		return whiteSheet;
	}

	public String getVersion() {
		return version;
	}

	public boolean getIsAsync() {
		return async;
	}

	public boolean getIsSupportRegister() {
		return isSupportRegister;
	}
}
