package miapd.ahpv2;

import Jama.Matrix;
import Jama.EigenvalueDecomposition;
import java.lang.*;

public class ComparisonMatrix {
    private final double[][] comparisonMatrix;
    private final int size;
    private String name = null;

    public ComparisonMatrix(int size){
        this.comparisonMatrix = new double[size][size];
        this.size = size;

        this.resetMatrix();
    }

    public ComparisonMatrix(int size, String name){
        this.comparisonMatrix = new double[size][size];
        this.size = size;
        this.name = name;

        this.resetMatrix();
    }

    private void resetMatrix(){
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                if (i == j) {
                    this.comparisonMatrix[i][j] = 1;
                }
                else {
                    this.comparisonMatrix[i][j] = 0;
                }
            }
        }
    }

    public void printMatrix(){
        for(int i=0; i<this.size; i++){
            for(int j=0; j<this.size; j++){
                System.out.print(comparisonMatrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private double[][] getAuxiliaryMatrixEV(){
        double[][] auxiliaryMatrix = new double[this.size][this.size];
        for(int i=0; i<this.size; i++){
            int zerosCounter = 0;
            for(int j=0; j<this.size; j++){
                if (i != j){
                    if(comparisonMatrix[i][j] == 0){
                        zerosCounter = zerosCounter + 1;
                    }
                    else{
                        auxiliaryMatrix[i][j] = comparisonMatrix[i][j];
                    }
                }
            }
            comparisonMatrix[i][i] = zerosCounter + 1;
        }

        return auxiliaryMatrix;
    }

    public double[] calculateEV(){
        Matrix matrix = new Matrix(this.getAuxiliaryMatrixEV());
        EigenvalueDecomposition EVD = new EigenvalueDecomposition(matrix);

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

        double[][] eigenvectors = EVD.getV().getArrayCopy();
        double[] eigenvector = new double[this.size];

        double sum = 0.0;
        for (int i=0; i<this.size; i++){
            eigenvector[i] = eigenvectors[i][maxIndex];
            sum = sum + eigenvector[i];
        }

        for (int i=0; i<this.size; i++){
            eigenvector[i] = eigenvector[i] / sum;
        }

        return eigenvector;
    }

    private double[][] getAuxiliaryMatrixGM(){
        double[][] auxiliaryMatrix = new double[this.size][this.size];

        for (int i=0; i<this.size; i++){
            int constantsCounter = 0;
            for (int j=0; j<this.size; j++){
                if (i != j){
                    if(comparisonMatrix[i][j] != 0){
                        constantsCounter = constantsCounter + 1;
                        auxiliaryMatrix[i][j] = 0;
                    }
                    else{
                        auxiliaryMatrix[i][j] = 1;
                    }
                }
            }
            auxiliaryMatrix[i][i] = constantsCounter + 1;
        }


        return auxiliaryMatrix;
    }

    private double[][] getConstantsMatrixGM(){
        double[][] constantsVector = new double[this.size][1];

        for (int i=0; i<this.size; i++){
            constantsVector[i][0] = 0;
            for(int j=0; j<this.size; j++){
                if(i != j){
                    if(this.comparisonMatrix[i][j] != 0){
                        constantsVector[i][0] = constantsVector[i][0] + Math.log(comparisonMatrix[i][j]);
                    }
                }
            }
        }

        return constantsVector;
    }

    public double[] calculateGM(){
        Matrix matrixB = new Matrix(this.getAuxiliaryMatrixGM());
        Matrix matrixLogC = new Matrix(this.getConstantsMatrixGM());
        Matrix matrixLogW = matrixB.solve(matrixLogC);

        double[][] tmpLogW = matrixLogW.getArrayCopy();
        double[] vectorW = new double[this.size];

        double sum = 0.0;
        for (int i=0; i<this.size; i++){
            double value = Math.exp(tmpLogW[i][0]);
            vectorW[i] = value;
            sum = sum + value;
        }

        for (int i=0; i<this.size; i++){
            vectorW[i] = vectorW[i]/sum;
        }

        return vectorW;
    }



}
