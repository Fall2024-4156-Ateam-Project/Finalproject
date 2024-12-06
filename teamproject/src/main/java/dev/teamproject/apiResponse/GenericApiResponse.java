package dev.teamproject.apiresponse;

/**
 * A generic API response wrapper that encapsulates a message, data, and success status.
 */
public class GenericApiResponse<T> {
  private String msg;
  private T data;
  private boolean isSuccess;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  public void setSuccess(boolean success) {
    isSuccess = success;
  }

  /**
   * Constructs a new {@code GenericApiResponse} with the specified 
   * message, data, and success status.
   */
  public GenericApiResponse(String msg, T data, boolean isSuccess) {
    this.msg = msg;
    this.data = data;
    this.isSuccess = isSuccess;
  }
}
