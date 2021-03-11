package processor;

public class Main {

    public static void main(String[] args) {

        MatrixOperationFactory operation = null;

        while (true) {

            printMenu();

            java.util.Scanner sc = new java.util.Scanner(System.in);

            int selection = sc.nextInt();

            switch (selection) {

                case 1 :
                    operation = MatrixOperationFactory.executeMatrixAddition();
                    break;

                case 2 :
                    operation = MatrixOperationFactory.executeMatrixScalarMultiplication();
                    break;

                case 3 :
                    operation = MatrixOperationFactory.executeMatrixMultiplication();
                    break;

                case 4:
                    operation = MatrixOperationFactory.executeMatrixTranspose();
                    break;

                case 5:
                    operation = MatrixOperationFactory.executeCalculateDeterminant();
                    break;

                case 6:
                    operation = MatrixOperationFactory.executeInverseMatrix();
                    break;

                case 0 :
                    return;

                default:
                    System.out.println("Invalid choice! Please choose again!");
                    break;
            }
        }
    }

    static void printMenu() {

        System.out.print("1. Add matrices\n" +
                "2. Multiply matrix by a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit\n" +
                "Your choice: ");
    }
}

abstract class MatrixOperationFactory {

    static java.util.Scanner scanner = new java.util.Scanner(System.in);
    boolean isIntegerMatrix = false;

    static MatrixAddition executeMatrixAddition() {
        return new MatrixAddition();
    }

    static MatrixScalarMultiplication executeMatrixScalarMultiplication() {
        return new MatrixScalarMultiplication();
    }

    static MatrixMultiplication executeMatrixMultiplication() {
        return new MatrixMultiplication();
    }

    static MatrixTranspose executeMatrixTranspose() {return new MatrixTranspose();}

    static CalculateDeterminant executeCalculateDeterminant() {return new CalculateDeterminant();}

    static InverseMatrix executeInverseMatrix() {return new InverseMatrix();}

    void printMatrix(double[][] matrix, int row, int column) {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

                if (isIntegerMatrix) {
                    System.out.print((int) matrix[i][j] + " ");
                } else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    double[][] readDoubleMatrix(int row, int column) {

        if (scanner.hasNextInt()) {
            isIntegerMatrix = true;
        }

        double[][] matrix = new double[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        return matrix;
    }
}

class MatrixAddition extends MatrixOperationFactory {

    MatrixAddition() {

        System.out.print("Enter size of first matrix: ");

        int firstMatrixRow = scanner.nextInt();
        int firstMatrixColumn = scanner.nextInt();

        System.out.println("Enter first matrix: ");
        double[][] firstMatrix = readDoubleMatrix(firstMatrixRow, firstMatrixColumn);

        System.out.print("Enter size of second matrix: ");

        int secondMatrixRow = scanner.nextInt();
        int secondMatrixColumn = scanner.nextInt();

        if (firstMatrixRow != secondMatrixRow || firstMatrixColumn != secondMatrixColumn) {
            System.out.println("Addition cannot be performed on matrices of different size.\n");
            return;
        }

        System.out.println("Enter second matrix: ");
        double[][] secondMatrix = readDoubleMatrix(secondMatrixRow, secondMatrixColumn);

        System.out.println("The result is: ");
        for (int i = 0; i < firstMatrixRow; i++) {
            for (int j = 0; j < firstMatrixColumn; j++) {

                if (isIntegerMatrix) {
                    System.out.print((int) (firstMatrix[i][j] + secondMatrix[i][j]) + " ");
                } else {
                    System.out.print((firstMatrix[i][j] + secondMatrix[i][j]) + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

class MatrixScalarMultiplication extends MatrixOperationFactory {

    MatrixScalarMultiplication() {

        System.out.print("Enter size of matrix : ");

        int row = scanner.nextInt();
        int column = scanner.nextInt();

        double[][] matrix = readDoubleMatrix(row, column);

        System.out.print("Enter constant : ");
        double constant = scanner.nextFloat();

        System.out.println("The result is: ");

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {

                if (isIntegerMatrix) {
                    System.out.print((int) (matrix[i][j] * constant) + " ");
                } else {
                    System.out.print((matrix[i][j] * constant) + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

class MatrixMultiplication extends MatrixOperationFactory {

    MatrixMultiplication() {

        System.out.print("Enter size of first matrix: ");

        int firstMatrixRow = scanner.nextInt();
        int firstMatrixColumn = scanner.nextInt();

        System.out.println("Enter first matrix: ");
        double[][] firstMatrix = readDoubleMatrix(firstMatrixRow, firstMatrixColumn);

        System.out.print("Enter size of second matrix: ");

        int secondMatrixRow = scanner.nextInt();
        int secondMatrixColumn = scanner.nextInt();

        if (firstMatrixColumn != secondMatrixRow) {
            System.out.println("Number of columns in first matrix and row of second matrix should be same.\n");
            return;
        }

        System.out.println("Enter second matrix: ");
        double[][] secondMatrix = readDoubleMatrix(secondMatrixRow, secondMatrixColumn);

        System.out.println("The result is: ");
        for (int i = 0; i < firstMatrixRow; i++) {
            for (int j = 0; j < secondMatrixColumn; j++) {

                double temp = 0;
                for (int k = 0; k < secondMatrixRow; k++) {

                    temp += firstMatrix[i][k] * secondMatrix[k][j];
                }

                if (isIntegerMatrix) {
                    System.out.print((int) temp + " ");
                } else {
                    System.out.print(temp + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

class MatrixTranspose extends MatrixOperationFactory {

    MatrixTranspose(){
        System.out.println();
        System.out.println("1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line");
        System.out.print("Your choice: ");

        int selection = scanner.nextInt();

        System.out.print("Enter matrix size: ");

        int row = scanner.nextInt();
        int column = scanner.nextInt();

        System.out.println("Enter matrix: ");
        double[][] matrix = readDoubleMatrix(row, column);

        double[][] result = null;

        switch (selection) {

            case 1:
                result = mainDiagonalTranspose(matrix, row, column);

                int temp = row;
                row = column;
                column = row;
                break;

            case 2:
                result = sideDiagonalTranspose(matrix, row, column);

                temp = row;
                row = column;
                column = row;
                break;

            case 3:
                result = verticalTranspose(matrix, row, column);
                break;

            case 4:
                result = horizontalTranspose(matrix, row, column);
                break;
        }

        System.out.println("The result is:");
        printMatrix(result, row, column);
        System.out.println();
    }

    public static double[][] mainDiagonalTranspose(double[][] matrix, int row, int column) {

        double[][] result = new double[column][row];

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                result[i][j] = matrix[j][i];
            }
        }
        return result;
    }

    private double[][] sideDiagonalTranspose(double[][] matrix, int row, int column) {

        double[][] result = new double[column][row];

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                result[i][j] = matrix[row - j - 1][column - i - 1];
            }
        }
        return result;
    }

    double[][] verticalTranspose(double[][] matrix, int row, int column) {

        double[][] result = new double[row][column];

        int i = 0;
        int j = column - 1;

        while (i < j) {
            for (int k = 0; k < row; k++) {
                result[k][i] = matrix[k][j];
                result[k][j] = matrix[k][i];
            }
            i++;
            j--;
        }
        if (i == j) {
            for (int k = 0; k < row; k++) {
                result[k][i] = matrix[k][i];
            }
        }
        return result;
    }

    double[][] horizontalTranspose(double[][] matrix, int row, int column) {

        double[][] result = new double[row][column];

        int i = 0;
        int j = row - 1;

        while (i < j) {
            for (int k = 0; k < column; k++) {
                result[i][k] = matrix[j][k];
                result[j][k] = matrix[i][k];
            }
            i++;
            j--;
        }
        if (i == j) {
            for (int k = 0; k < column; k++) {
                result[i][k] = matrix[i][k];
            }
        }
        return result;
    }
}

class CalculateDeterminant extends MatrixOperationFactory {

    CalculateDeterminant() {

        System.out.print("Enter matrix size: ");
        int row = scanner.nextInt();
        int column = scanner.nextInt();

        if (row != column) {
            System.out.println("Error: Size of row and column can't be different.");
            return;
        }

        System.out.println("Enter matrix:");
        double[][] matrix = readDoubleMatrix(row, column);
        double determinant = findDeterminant(matrix, row, column);

        System.out.println("The result is:");

        if (isIntegerMatrix) {
            System.out.println((int) determinant);
        } else {
            System.out.println(determinant);
        }

        System.out.println();
    }

    public static double findDeterminant(double[][] matrix, int row, int column) {

        if (row ==  1 && column == 1) {
            return matrix[0][0];
        }

        if (row == 2 && column == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double determinant = 0;

        for (int i = 0; i < column; i++) {

            if (matrix[0][i] == 0) {
                continue;
            }

            double[][] newMatrix = new double[row - 1][column - 1];

            for (int j = 1, a = 0; j < row; j++, a++) {
                for (int k = 0, b = 0; k < column; k++, b++) {

                    if (k == i) {
                        b--;
                        continue;
                    }
                    newMatrix[a][b] = matrix[j][k];
                }
            }
            double temp = (i % 2 == 0) ?
                    matrix[0][i] * findDeterminant(newMatrix, row - 1, column - 1) :
                    (matrix[0][i] * findDeterminant(newMatrix, row - 1, column - 1)) * (-1);

            determinant += temp;
        }
        return determinant;
    }
}

class InverseMatrix extends MatrixOperationFactory {

    InverseMatrix() {

        System.out.print("Enter matrix size: ");
        int row = scanner.nextInt();
        int column = scanner.nextInt();

        if (row != column) {
            System.out.println("Error: Size of row and column can't be different.");
            return;
        }

        System.out.println("Enter matrix:");
        double[][] matrix = readDoubleMatrix(row, column);

        double determinant = CalculateDeterminant.findDeterminant(matrix, row, column);

        if (determinant == 0) {
            System.out.println("The matrix doesn't have an inverse.");
            return;
        }
        double[][] cofactor = cofactor(matrix, row, column);

        cofactor = MatrixTranspose.mainDiagonalTranspose(cofactor, row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.printf("%5.2f ", (1.0 / determinant) * cofactor[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private double[][] cofactor(double[][] matrix, int row, int column) {

        double[][] res = new double[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

                double[][] newMatrix = new double[row - 1][column - 1];

                for (int x = 0, a = 0; x < row; x++, a++) {

                    if (x == i) {
                        a--;
                        continue;
                    }

                    for (int y = 0, b = 0; y < column; y++, b++) {

                        if (y == j) {
                            b--;
                            continue;
                        }
                        newMatrix[a][b] = matrix[x][y];
                    }
                }
                res[i][j] = (i + j) % 2 == 1 ?
                        CalculateDeterminant.findDeterminant(newMatrix, row - 1, column - 1) * -1 :
                        CalculateDeterminant.findDeterminant(newMatrix, row - 1, column - 1);
            }
        }
        return res;
    }
}