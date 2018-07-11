package com.alipay.pussycat.core.common.model;

/**
 *  @author wb-smj330392
 * @create 2018-07-09 15:11
 */
public class AckCustomBody implements TransportBody {
	
	/**   request请求id   **/
	private long requestId;

	/**   是否消费处理成功   **/
	private boolean success;
	
    
	public AckCustomBody(long requestId, boolean success) {
		this.requestId = requestId;
		this.success = success;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "AckCustomBody [requestId=" + requestId + ", success=" + success + "]";
	}
	

}
