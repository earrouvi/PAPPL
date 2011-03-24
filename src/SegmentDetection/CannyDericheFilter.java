package SegmentDetection;
//package extractLineUtils;

import ij.*;
import ij.process.*;

/**
 *  Canny-Deriche filter
 *
 *@author   thomas.boudier@snv.jussieu.fr
 *@created	26 aout 2003
 */
public class CannyDericheFilter{
	
	protected ImagePlus fenetre;
	protected static double[] norm_deriche;
	protected static double[] angle_deriche;
	protected static ImagePlus suppRes;
	
	/**
	 * Default constructor
	 */
	public CannyDericheFilter(){

	}


	/**
	 *  Main processing method
	 *
	 *@param  	imp  	image
	 *@param	alphaD	
	 *@return			ImagePlus
	 */
	public static ImagePlus processDeriche(ImagePlus imp, float alphaD) {
		
		FloatProcessor tmp = new FloatProcessor(imp.getWidth(), imp.getHeight());
		
		ImageStack stack = imp.getStack();
		ImageStack res_norm = new ImageStack(stack.getWidth(), stack.getHeight());
		ImageStack res_angle = new ImageStack(stack.getWidth(), stack.getHeight());
		ImageStack suppr = new ImageStack(stack.getWidth(), stack.getHeight());
		
		deriche(imp.getProcessor(), alphaD);
		tmp = new FloatProcessor(imp.getWidth(), imp.getHeight(), norm_deriche);
		tmp.resetMinAndMax();
		res_norm.addSlice("", tmp);
		tmp = new FloatProcessor(stack.getWidth(), stack.getHeight(), angle_deriche);
		tmp.resetMinAndMax();
		res_angle.addSlice("", tmp);
		suppr.addSlice("", nonMaximalSuppression(res_norm.getProcessor(1), tmp));

		//new ImagePlus("Canny-Deriche norm " + alphaD, res_norm).show();
		suppRes = new ImagePlus("Canny, alpha = " + alphaD, suppr);
		//suppRes.show();
		return suppRes;
	}


	/**
	 *  Filter
	 *
	 *@param  ip      image
	 *@param  alphaD  alpha coefficient
	 */
	private static void deriche(ImageProcessor ip, float alphaD) {
		norm_deriche = null;
		angle_deriche = null;

		int nmem;
		float[] nf_grx = null;
		float[] nf_gry = null;
		int[] a1 = null;
		float[] a2 = null;
		float[] a3 = null;
		float[] a4 = null;

		int icolonnes = 0;
		int lignes;
		int colonnes;
		int lig_1;
		int lig_2;
		int lig_3;
		int col_1;
		int col_2;
		int col_3;
		int icol_1;
		int icol_2;
		int i;
		int j;
		float ad1;
		float ad2;
		float an1;
		float an2;
		float an3;
		float an4;
		float an11;

		lignes = ip.getHeight();
		colonnes = ip.getWidth();
		nmem = lignes * colonnes;

		lig_1 = lignes - 1;
		lig_2 = lignes - 2;
		lig_3 = lignes - 3;
		col_1 = colonnes - 1;
		col_2 = colonnes - 2;
		col_3 = colonnes - 3;

		/*
		 *  alloc temporary buffers
		 */
		norm_deriche = new double[nmem];
		angle_deriche = new double[nmem];

		nf_grx = new float[nmem];
		nf_gry = new float[nmem];

		a1 = new int[nmem];
		a2 = new float[nmem];
		a3 = new float[nmem];
		a4 = new float[nmem];

		ad1 = (float) -Math.exp(-alphaD);
		ad2 = 0;
		an1 = 1;
		an2 = 0;
		an3 = (float) Math.exp(-alphaD);
		an4 = 0;
		an11 = 1;

		/*
		 *  FIRST STEP:  Y GRADIENT
		 */
		/*
		 *  x-smoothing
		 */
		for (i = 0; i < lignes; i++) {
			for (j = 0; j < colonnes; j++) {
				a1[i * colonnes + j] = (int) ip.getPixel(j, i);
			}
		}

		for (i = 0; i < lignes; ++i) {
			icolonnes = i * colonnes;
			icol_1 = icolonnes - 1;
			icol_2 = icolonnes - 2;
			a2[icolonnes] = an1 * a1[icolonnes];
			a2[icolonnes + 1] = an1 * a1[icolonnes + 1] +
					an2 * a1[icolonnes] - ad1 * a2[icolonnes];
			for (j = 2; j < colonnes; ++j) {
				a2[icolonnes + j] = an1 * a1[icolonnes + j] + an2 * a1[icol_1 + j] -
						ad1 * a2[icol_1 + j] - ad2 * a2[icol_2 + j];
			}
		}

		for (i = 0; i < lignes; ++i) {
			icolonnes = i * colonnes;
			icol_1 = icolonnes + 1;
			icol_2 = icolonnes + 2;
			a3[icolonnes + col_1] = 0;
			a3[icolonnes + col_2] = an3 * a1[icolonnes + col_1];
			for (j = col_3; j >= 0; --j) {
				a3[icolonnes + j] = an3 * a1[icol_1 + j] + an4 * a1[icol_2 + j] -
						ad1 * a3[icol_1 + j] - ad2 * a3[icol_2 + j];
			}
		}

		icol_1 = lignes * colonnes;

		for (i = 0; i < icol_1; ++i) {
			a2[i] += a3[i];
		}

		/*
		 *  FIRST STEP Y-GRADIENT : y-derivative
		 */
		/*
		 *  columns top - downn
		 */
		for (j = 0; j < colonnes; ++j) {
			a3[j] = 0;
			a3[colonnes + j] = an11 * a2[j] - ad1 * a3[j];
			for (i = 2; i < lignes; ++i) {
				a3[i * colonnes + j] = an11 * a2[(i - 1) * colonnes + j] -
						ad1 * a3[(i - 1) * colonnes + j] - ad2 * a3[(i - 2) * colonnes + j];
			}
		}

		/*
		 *  columns down top
		 */
		for (j = 0; j < colonnes; ++j) {
			a4[lig_1 * colonnes + j] = 0;
			a4[(lig_2 * colonnes) + j] = -an11 * a2[lig_1 * colonnes + j] -
					ad1 * a4[lig_1 * colonnes + j];
			for (i = lig_3; i >= 0; --i) {
				a4[i * colonnes + j] = -an11 * a2[(i + 1) * colonnes + j] -
						ad1 * a4[(i + 1) * colonnes + j] - ad2 * a4[(i + 2) * colonnes + j];
			}
		}

		icol_1 = colonnes * lignes;
		for (i = 0; i < icol_1; ++i) {
			a3[i] += a4[i];
		}

		for (i = 0; i < lignes; ++i) {
			for (j = 0; j < colonnes; ++j) {
				nf_gry[i * colonnes + j] = a3[i * colonnes + j];
			}
		}

		/*
		 *  SECOND STEP X-GRADIENT
		 */
		for (i = 0; i < lignes; ++i) {
			for (j = 0; j < colonnes; ++j) {
				a1[i * colonnes + j] = (int) (ip.getPixel(j, i));
			}
		}

		for (i = 0; i < lignes; ++i) {
			icolonnes = i * colonnes;
			icol_1 = icolonnes - 1;
			icol_2 = icolonnes - 2;
			a2[icolonnes] = 0;
			a2[icolonnes + 1] = an11 * a1[icolonnes];
			for (j = 2; j < colonnes; ++j) {
				a2[icolonnes + j] = an11 * a1[icol_1 + j] -
						ad1 * a2[icol_1 + j] - ad2 * a2[icol_2 + j];
			}
		}

		for (i = 0; i < lignes; ++i) {
			icolonnes = i * colonnes;
			icol_1 = icolonnes + 1;
			icol_2 = icolonnes + 2;
			a3[icolonnes + col_1] = 0;
			a3[icolonnes + col_2] = -an11 * a1[icolonnes + col_1];
			for (j = col_3; j >= 0; --j) {
				a3[icolonnes + j] = -an11 * a1[icol_1 + j] -
						ad1 * a3[icol_1 + j] - ad2 * a3[icol_2 + j];
			}
		}
		icol_1 = lignes * colonnes;
		for (i = 0; i < icol_1; ++i) {
			a2[i] += a3[i];
		}

		/*
		 *  on the columns
		 */
		/*
		 *  columns top down
		 */
		for (j = 0; j < colonnes; ++j) {
			a3[j] = an1 * a2[j];
			a3[colonnes + j] = an1 * a2[colonnes + j] + an2 * a2[j]
					 - ad1 * a3[j];
			for (i = 2; i < lignes; ++i) {
				a3[i * colonnes + j] = an1 * a2[i * colonnes + j] + an2 * a2[(i - 1) * colonnes + j] -
						ad1 * a3[(i - 1) * colonnes + j] - ad2 * a3[(i - 2) * colonnes + j];
			}
		}

		/*
		 *  columns down top
		 */
		for (j = 0; j < colonnes; ++j) {
			a4[lig_1 * colonnes + j] = 0;
			a4[lig_2 * colonnes + j] = an3 * a2[lig_1 * colonnes + j] - ad1 * a4[lig_1 * colonnes + j];
			for (i = lig_3; i >= 0; --i) {
				a4[i * colonnes + j] = an3 * a2[(i + 1) * colonnes + j] + an4 * a2[(i + 2) * colonnes + j] -
						ad1 * a4[(i + 1) * colonnes + j] - ad2 * a4[(i + 2) * colonnes + j];
			}
		}

		icol_1 = colonnes * lignes;
		for (i = 0; i < icol_1; ++i) {
			a3[i] += a4[i];
		}

		for (i = 0; i < lignes; i++) {
			for (j = 0; j < colonnes; j++) {
				nf_grx[i * colonnes + j] = a3[i * colonnes + j];
			}
		}

		/*
		 *  SECOND STEP X-GRADIENT : the x-gradient is  done
		 */
		/*
		 *  THIRD STEP : NORM
		 */
		/*
		 *  computation of the magnitude and angle
		 */
		for (i = 0; i < lignes; i++) {
			for (j = 0; j < colonnes; j++) {
				a2[i * colonnes + j] = nf_gry[i * colonnes + j];
			}
		}
		icol_1 = colonnes * lignes;
		for (i = 0; i < icol_1; ++i) {
			norm_deriche[i] = modul(nf_grx[i], nf_gry[i]);
			angle_deriche[i] = angle(nf_grx[i], nf_gry[i]);
		}
	}


	/**
	 *  Suppression of non local-maxima
	 *
	 *@param  grad  the norm gradient image
	 *@param  ang   the angle gradient image
	 *@return       the image with non local-maxima suppressed
	 */
	static ImageProcessor nonMaximalSuppression(ImageProcessor grad, ImageProcessor ang) {
		FloatProcessor res = new FloatProcessor(grad.getWidth(), grad.getHeight());

		int la = grad.getWidth();
		int ha = grad.getHeight();

		float ag;
		float pix1 = 0;
		float pix2 = 0;
		float pix;

		for (int x = 1; x < la - 1; x++) {
			for (int y = 1; y < ha - 1; y++) {
				ag = ang.getPixelValue(x, y);
				if ((ag > -22.5) && (ag <= 22.5)) {
					pix1 = grad.getPixelValue(x + 1, y);
					pix2 = grad.getPixelValue(x - 1, y);
				} else if ((ag > 22.5) && (ag <= 67.5)) {
					pix1 = grad.getPixelValue(x + 1, y - 1);
					pix2 = grad.getPixelValue(x - 1, y + 1);
				} else if (((ag > 67.5) && (ag <= 90)) || ((ag < -67.5) && (ag >= -90))) {
					pix1 = grad.getPixelValue(x, y - 1);
					pix2 = grad.getPixelValue(x, y + 1);
				} else if ((ag < -22.5) && (ag >= -67.5)) {
					pix1 = grad.getPixelValue(x + 1, y + 1);
					pix2 = grad.getPixelValue(x - 1, y - 1);
				}
				pix = grad.getPixelValue(x, y);
				if ((pix >= pix1) && (pix >= pix2)) {
					res.putPixelValue(x, y, pix);
				}
			}
		}
		return res;
	}


	/**
	 *  modul
	 *
	 *@param  dx  derivative in x
	 *@param  dy  derivative in y
	 *@return     norm of gradient
	 */
	public static double modul(float dx, float dy) {
		return (Math.sqrt(dx * dx + dy * dy));
	}


	/**
	 *  angle
	 *
	 *@param  dx  derivative in x
	 *@param  dy  derivative in y
	 *@return     angle of gradient
	 */
	public static double angle(float dx, float dy) {
		return (-Math.toDegrees(Math.atan(dy / dx)));
	}

}

