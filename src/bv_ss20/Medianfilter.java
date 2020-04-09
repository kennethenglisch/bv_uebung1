package bv_ss20;

import java.util.Arrays;

public class Medianfilter implements Filter {

	private RasterImage source;
	private RasterImage destination;
	private int kernelWidth;
	private int kernelHeight;
	
	@Override
	public void setSourceImage(RasterImage sourceImage) {
		this.source = sourceImage;
	}

	@Override
	public void setDestinationImage(RasterImage destinationImage) {
		this.destination = destinationImage;
	}

	@Override
	public void setKernelWidth(int kernelWidth) {
		this.kernelWidth = kernelWidth;
	}

	@Override
	public void setKernelHeight(int kernelHeight) {
		this.kernelHeight = kernelHeight;
	}

	@Override
	public void apply() 
	{
		int height = source.height;
        int width = source.width;
        int argbS[] = source.argb;
        int argbD[] = destination.argb;

        int kW = kernelWidth / 2;
        int kH = kernelHeight / 2;

        int imin = 0 - kW;
        int jmin = 0 - kH;

        int imax = kW;
        int jmax = kH;
        
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {

                int pos = y*width + x;
                
                int[] kernel = new int[kernelWidth * kernelHeight];
                int count = 0;
                int middle = kernel.length / 2;
                
                for (int i = imin; i <= imax; i++)
                    for (int j = jmin; j <= jmax; j++) 
                    {
                    	
                        int l = i;
                        int m = j;

                        if(y + i < 0 || y + i >= height) 
                            l = 0;
                        if(x + j < 0 || x + j >= width) 
                            m = 0;

                        int posK = (y + l) * width + (x + m);
                        int pixK = argbS[posK];
                        int rk = (pixK >> 16) & 0xff;

                        kernel[count] = rk;
                        count++;
                    }
                
                Arrays.sort(kernel);
                int median = kernel[middle];
                

                argbD[pos] = (0xFF<<24) | (median<<16) | (median<<8) | median;
                
            }

        }
	}
}
