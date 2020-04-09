package bv_ss20;

import java.util.Arrays;

public class MaximumsFilter implements Filter {

	private RasterImage source;
	private RasterImage DestinationImage;
	private int kernelSize;
	
	@Override
	public void setSourceImage(RasterImage sourceImage) {
		this.source = sourceImage;
	}

	@Override
	public void setDestinationImage(RasterImage destinationImage) {
		this.DestinationImage = destinationImage;
	}

	@Override
	public void setKernelWidth(int kernelWidth) {
		this.kernelSize = kernelWidth;
	}

	@Override
	public void setKernelHeight(int kernelHeight) {
	}

	@Override
	public void apply() {
		int height = source.height;
		int width = source.width;
		int argbS[] = source.argb;
		int argbD[] = DestinationImage.argb;
		int minR = 0;
		
		int k = kernelSize/2;
		
		int imin = 0 - k;
		int jmin = 0 - k;
		
		int imax = k;
		int jmax = k;
		
		
		for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {

                int pos = y*width + x;

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
                        
                        if(minR < rk)
                        	minR = rk;
                    }
            	
                argbD[pos] = (0xFF<<24) | (minR<<16) | (minR<<8) | minR;
            	minR = 0;
            }

        }
	}

}
