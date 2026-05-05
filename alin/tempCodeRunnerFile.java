import java.util.Scanner;

public class OBE {

    static Scanner input = new Scanner(System.in);

    static void tampilMatriks(double[][] m) {
        for (double[] row : m) {
            System.out.print("| ");
            for (double val : row) {
                System.out.printf("%8.2f ", val);
            }1
            System.out.println("|");
        }
    }

    static double[][] inputMatriks(int baris, int kolom) {
    double[][] m = new double[baris][kolom];
    System.out.println("Masukkan elemen matriks:");
    for (int i = 0; i < baris; i++) {
        for (int j = 0; j < kolom; j++) {
            System.out.print("[" + (i+1) + "][" + (j+1) + "] = ");
            m[i][j] = input.nextDouble();
        }
    }
    return m;
}
    static double det2x2(double[][] m) {
        return m[0][0]*m[1][1] - m[0][1]*m[1][0];
    }

    static double det3x3(double[][] m) {
        return m[0][0]*(m[1][1]*m[2][2] - m[1][2]*m[2][1])
             - m[0][1]*(m[1][0]*m[2][2] - m[1][2]*m[2][0])
             + m[0][2]*(m[1][0]*m[2][1] - m[1][1]*m[2][0]);
    }

    // ================= INVERS =================
    static double[][] invers2x2(double[][] m) {
        double det = det2x2(m);
        if (det == 0) return null;

        return new double[][]{
            { m[1][1]/det, -m[0][1]/det },
            { -m[1][0]/det, m[0][0]/det }
        };
    }

    static double[][] invers3x3(double[][] m) {
        double det = det3x3(m);
        if (det == 0) return null;

        double[][] inv = new double[3][3];

        inv[0][0] =  (m[1][1]*m[2][2] - m[1][2]*m[2][1]);
        inv[0][1] = -(m[0][1]*m[2][2] - m[0][2]*m[2][1]);
        inv[0][2] =  (m[0][1]*m[1][2] - m[0][2]*m[1][1]);

        inv[1][0] = -(m[1][0]*m[2][2] - m[1][2]*m[2][0]);
        inv[1][1] =  (m[0][0]*m[2][2] - m[0][2]*m[2][0]);
        inv[1][2] = -(m[0][0]*m[1][2] - m[0][2]*m[1][0]);

        inv[2][0] =  (m[1][0]*m[2][1] - m[1][1]*m[2][0]);
        inv[2][1] = -(m[0][0]*m[2][1] - m[0][1]*m[2][0]);
        inv[2][2] =  (m[0][0]*m[1][1] - m[0][1]*m[1][0]);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inv[i][j] /= det;
            }
        }

        return inv;
    }

    // ================= OBE 2 VARIABEL =================
    static void obe2(double[][] aug) {
        double m = aug[1][0] / aug[0][0];

        for (int j = 0; j < 3; j++) {
            aug[1][j] -= m * aug[0][j];
        }

        double y = aug[1][2] / aug[1][1];
        double x = (aug[0][2] - aug[0][1] * y) / aug[0][0];

        double[][] hasil = {{x},{y}};
        System.out.println("Hasil:");
        tampilMatriks(hasil);
    }

    // ================= OBE 3 VARIABEL =================
    static void obe3(double[][] aug) {

        // eliminasi baris 2 & 3 terhadap baris 1
        for (int i = 1; i < 3; i++) {
            double m = aug[i][0] / aug[0][0];
            for (int j = 0; j < 4; j++) {
                aug[i][j] -= m * aug[0][j];
            }
        }

        // eliminasi baris 3 terhadap baris 2
        double m2 = aug[2][1] / aug[1][1];
        for (int j = 1; j < 4; j++) {
            aug[2][j] -= m2 * aug[1][j];
        }

        // back substitution
        double z = aug[2][3] / aug[2][2];
        double y = (aug[1][3] - aug[1][2]*z) / aug[1][1];
        double x = (aug[0][3] - aug[0][1]*y - aug[0][2]*z) / aug[0][0];

        double[][] hasil = {{x},{y},{z}};
        System.out.println("Hasil:");
        tampilMatriks(hasil);
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        int pilih;
        do {
            System.out.println("\n=== PROGRAM MATRIKS ===");
            System.out.println("1. Determinan 2x2");
            System.out.println("2. Determinan 3x3");
            System.out.println("3. Invers 2x2");
            System.out.println("4. Invers 3x3");
            System.out.println("5. OBE 2 Variabel");
            System.out.println("6. OBE 3 Variabel");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            pilih = input.nextInt();

            switch (pilih) {
                case 1:
                    tampilMatriks(inputMatriks(2,2));
                    break;

                case 2:
                    double[][] m3 = inputMatriks(3,3);
                    tampilMatriks(m3);
                    System.out.println("Determinan = " + det3x3(m3));
                    break;

                case 3:
                    double[][] m2 = inputMatriks(2,2);
                    double[][] inv2 = invers2x2(m2);
                    if (inv2 != null) tampilMatriks(inv2);
                    else System.out.println("Tidak punya invers");
                    break;

                case 4:
                    double[][] m33 = inputMatriks(3,3);
                    double[][] inv3 = invers3x3(m33);
                    if (inv3 != null) tampilMatriks(inv3);
                    else System.out.println("Tidak punya invers");
                    break;

                case 5:
                    obe2(inputMatriks(2,3));
                    break;

                case 6:
                    obe3(inputMatriks(3,4));
                    break;

                case 0:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Menu tidak tersedia!");
            }

        } while (pilih != 0);
    }
}