package techproed.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class ListenersRetry implements IRetryAnalyzer {

    // BU SINIF SADECE FAIL OLAN TEST CASE LERI TEKRAR CALISTIRIR
    private int retryCount = 0;

    private static final int maxRetryCount = 2;  //maxRetryCount EK OLARAK CALISMA SAYISI.BU ORNEKTE FAIL OLAN TESTLER EXTRA 1 KEZ DAHA CALISIR

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount<maxRetryCount){
            retryCount++;
            return true;
        }
        return false;
    }
}