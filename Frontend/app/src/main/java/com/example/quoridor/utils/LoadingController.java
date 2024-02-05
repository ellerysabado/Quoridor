package com.example.quoridor.utils;

/**
 * Interface for showing and hiding loading notification for UI blocking elements.
 *
 * @author Carter Awbrey
 * */
public interface LoadingController {

    public void onCreate();
    public void StartLoading();
    public void FinishLoading();
}
