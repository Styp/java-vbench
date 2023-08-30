package ch.styp;

public class SimpleMatrixMul {

    public float[] baseline(float[] a, float[] b, float[] result, int n){
        return baselineIJK(a, b, result, n);
    }

    public float[] baselineIJK(float[] a, float[] b, float[] result, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                float sum = 0.0f;
                for (int k = 0; k < n; k++) {
                    sum += a[i * n + k] * b[k * n + j];
                }
                result[i * n + j] = sum;
            }
        }
        return result;
    }

    public float[] baselineJIK(float[] a, float[] b, float[] result, int n) {
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                float sum = 0.0f;
                for (int k = 0; k < n; k++) {
                    sum += a[i * n + k] * b[k * n + j];
                }
                result[i * n + j] = sum;
            }
        }
        return result;
    }

    public float[] baselineIKJ(float[] a, float[] b, float[] result, int n) {
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                float aik = a[i * n + k];
                for (int j = 0; j < n; j++) {
                    result[i * n + j] += aik * b[k * n + j];
                }
            }
        }
        return result;
    }

    public float[] baselineKIJ(float[] a, float[] b, float[] result, int n) {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                float aik = a[i * n + k];
                for (int j = 0; j < n; j++) {
                    result[i * n + j] += aik * b[k * n + j];
                }
            }
        }
        return result;
    }

    public float[] baselineJKI(float[] a, float[] b, float[] result, int n) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                float bjk = b[k * n + j];
                for (int i = 0; i < n; i++) {
                    result[i * n + j] += a[i * n + k] * bjk;
                }
            }
        }
        return result;
    }


    public float[] baselineKJI(float[] a, float[] b, float[] result, int n) {
        for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
                float bjk = b[k * n + j];
                for (int i = 0; i < n; i++) {
                    result[i * n + j] += a[i * n + k] * bjk;
                }
            }
        }
        return result;
    }



}
