package ch.styp;

public class Convolution2D {

    public static double[][] GenerateGaussianKernel2D(int kernelSize, float sigma) {
        var kernel = new double[kernelSize][kernelSize];
        var kernelRadius = kernelSize / 2;

        var calculatedEuler = 1.0 / (2.0 * Math.PI * Math.pow(sigma, 2));

        for (var filterY = -kernelRadius; filterY <= kernelRadius; filterY++) {
            for (var filterX = -kernelRadius; filterX <= kernelRadius; filterX++) {
                double distance = ((filterX * filterX) + (filterY * filterY)) / (2 * (sigma * sigma));
                kernel[filterX + kernelRadius][filterY + kernelRadius] = calculatedEuler * Math.exp(-distance);
            }
        }
        return kernel;
    }

    public static double[][] convolveImageLegacy(double[][] image2D, double[][] kernel) {

        var processedXSize = image2D.length - kernel.length + 1;
        var processedYSize = image2D[0].length - kernel[0].length + 1;

        // Kernel is quadratic, and sperable therefor 3x3, 4x4 etc...
        var kernelSize = kernel.length;

        var processedImage = new double[processedXSize][processedYSize];

        for (var y = 0; y < processedYSize; y++) {
            for (var x = 0; x < processedXSize; x++) {
                for (var slidingY = y; slidingY < y + kernelSize; slidingY++) {
                    for (var slidingX = x; slidingX < x + kernelSize; slidingX++) {
                        processedImage[x][y] += kernel[slidingX - x][slidingY - y] * image2D[slidingX][slidingY];
                    }
                }
            }
        }
        return processedImage;
    }

}
