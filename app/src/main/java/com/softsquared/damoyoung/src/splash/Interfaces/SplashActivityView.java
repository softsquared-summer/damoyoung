package com.softsquared.damoyoung.src.splash.Interfaces;

public interface SplashActivityView {

    void validateSuccess(String text,String jwt);
    void validateFailure(String message);
}
