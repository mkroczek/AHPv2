package miapd.ahp.ahp;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import miapd.ahp.objects.ComparisonMatrix;
import miapd.ahp.utils.SingularValueError;

public class ConsistencyIndices {

    public static double calculateSaatysIC(ComparisonMatrix matrix){
        Matrix auxilaryMatrix = new Matrix(matrix.getAuxiliaryMatrixEV());
        EigenvalueDecomposition EVD = new EigenvalueDecomposition(auxilaryMatrix);

        double[] eigenvaluesR = EVD.getRealEigenvalues();
        double[] eigenvaluesI = EVD.getImagEigenvalues();

        double max = -1.0;
        int maxIndex = 0;
        for(int i=0; i<eigenvaluesR.length; i++){
            if (eigenvaluesR[i] > max && eigenvaluesI[i] == 0.0){
                max = eigenvaluesR[i];
                maxIndex = i;
            }
        }

        return (max - matrix.getSize())/(matrix.getSize() - 1);
    }

    public static double calculateGCI(ComparisonMatrix matrix) throws SingularValueError {
        double[] vectorW = matrix.calculateGM();

        int counter = 0;
        double consistencyIndex = 0.0;

        for(int i = 0; i<matrix.getSize(); i++){
            for (int j = i+1; j< matrix.getSize(); j++){
                if(matrix.getValueFromIndex(i,j) != 0){
                    counter = counter + 1;
                    consistencyIndex = consistencyIndex +
                        Math.pow(Math.log(matrix.getValueFromIndex(i,j)*vectorW[i]/vectorW[j]), 2);
                }
            }
        }

        //Z ksiązki
        if(matrix.checkIfComplete()){
            counter = counter * matrix.getSize()/(matrix.getSize() - 2);
        }

        return consistencyIndex/counter;
    }
}
