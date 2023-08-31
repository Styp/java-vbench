package ch.styp;

public class BlockedMatrixMul {

    public float[] blockedIJK(float[] a, float[] b, float[] result, int n, final int blocksize) {
        for (int kk = 0; kk < n; kk += blocksize) {
            for (int jj = 0; jj < n; jj += blocksize) {
                for (int i = 0; i < n; i++) {
                    for (int j = jj; j < jj + blocksize; ++j) {
                        float sum = result[i * n + j];
                        for (int k = kk; k < kk + blocksize; ++k) {
                            sum += a[i * n + k] * b[k * n + j];
                        }
                        result[i * n + j] = sum;
                    }
                }
            }
        }
        return result;
    }

    public float[] blockedIKJ(float[] a, float[] b, float[] result, int n, final int blocksize) {
        for (int kk = 0; kk < n; kk += blocksize) {
            for (int jj = 0; jj < n; jj += blocksize) {
                for (int i = 0; i < n; i++) {
                    for (int k = kk; k < kk + blocksize; ++k) {
                        float aik = a[i * n + k];
                        for (int j = jj; j < jj + blocksize; ++j) {
                            result[i * n + j] += aik * b[k * n + j];
                        }
                    }
                }
            }
        }
        return result;
    }
}
