import java.util.Scanner;

 npublic class AljabarLinear {

    static Scanner input = new Scanner(System.in);

    // ================= TAMPIL =================
   static void tampilMatriks(double[][] m) {
    for (double[] row : m) {
        System.out.print("| ");
        for (double val : row) {

            // cek apakah bilangan bulat
            if (val == (int) val) {
                System.out.printf("%8d ", (int) val); // tanpa .00
            } else {
                System.out.printf("%8.2f ", val); // tetap desimal
            }

        }
        System.out.println("|");
    }
}

    // ================= INPUT =================
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

    // ================= AMBIL KOEFISIEN =================
    static double[][] ambilMatriksKoefisien(double[][] aug, int ukuran) {
        double[][] coeff = new double[ukuran][ukuran];
        for(int i = 0; i < ukuran; i++){
            for(int j = 0; j < ukuran; j++){
                coeff[i][j] = aug[i][j];
            }
        }
        return coeff;
    }

    // ================= DETERMINAN =================
    static double det2x2(double[][] m) {
        return m[0][0]*m[1][1] - m[0][1]*m[1][0];
    }

    static double det3x3(double[][] m) {
        return m[0][0]*(m[1][1]*m[2][2] - m[1][2]*m[2][1])
             - m[0][1]*(m[1][0]*m[2][2] - m[1][2]*m[2][0])
             + m[0][2]*(m[1][0]*m[2][1] - m[1][1]*m[2][0]);
    }

    // ================= INVERS =================
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

    // ================= OBE 3 VARIABEL =================
   static void obe3(double[][] aug) {

    System.out.println("\n=== PROSES OBE ===");

    // STEP 1
    System.out.println("\n[STEP 1] Matriks awal:");
    tampilMatriks(aug);

    // eliminasi baris 2 & 3 terhadap baris 1
    for (int i = 1; i < 3; i++) {
        double m = aug[i][0] / aug[0][0];
        System.out.println("\nEliminasi R" + (i+1) + " = R" + (i+1) + " - (" + m + " * R1)");

        for (int j = 0; j < 4; j++) {
            aug[i][j] -= m * aug[0][j];
        }

        tampilMatriks(aug);
    }

    // STEP 2
    double m2 = aug[2][1] / aug[1][1];
    System.out.println("\nEliminasi R3 = R3 - (" + m2 + " * R2)");

    for (int j = 1; j < 4; j++) {
        aug[2][j] -= m2 * aug[1][j];
    }

    tampilMatriks(aug);

    // STEP 3 BACK SUBSTITUTION
    System.out.println("\n[STEP 3] Substitusi balik:");

    double z = aug[2][3] / aug[2][2];
    System.out.println("z = " + aug[2][3] + " / " + aug[2][2] + " = " + z);

    double y = (aug[1][3] - aug[1][2]*z) / aug[1][1];
    System.out.println("y = (" + aug[1][3] + " - " + aug[1][2] + "*" + z + ") / " + aug[1][1] + " = " + y);

    double x = (aug[0][3] - aug[0][1]*y - aug[0][2]*z) / aug[0][0];
    System.out.println("x = (" + aug[0][3] + " - " + aug[0][1] + "*" + y + " - " + aug[0][2] + "*" + z + ") / " + aug[0][0] + " = " + x);

    // HASIL AKHIR
    System.out.println("\n=== HASIL AKHIR ===");
    double[][] hasil = {{x},{y},{z}};
    tampilMatriks(hasil);
}

    // ================= MAIN =================
    public static void main(String[] args) {

        int pilih;
        do {
            System.out.println("\n=== PROGRAM MATRIKS LENGKAP ===");
            System.out.println("1. Proses Lengkap 3 Variabel");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            pilih = input.nextInt();

            switch (pilih) {
                case 1:
                    System.out.println("\n-- INPUT MATRIKS AUGMENTED 3x4 --");
                    double[][] aug3 = inputMatriks(3,4);

                    System.out.println("\n[ MATRIKS AUGMENTED ]");
                    tampilMatriks(aug3);

                    double[][] coeff3 = ambilMatriksKoefisien(aug3, 3);
                    System.out.println("\n[ MATRIKS KOEFISIEN ]");
                    tampilMatriks(coeff3);

                    double det3 = det3x3(coeff3);
                    System.out.println("\n1. DETERMINAN = " + det3);

                    System.out.println("\n2. INVERS:");
                    double[][] inv3 = invers3x3(coeff3);
                    if (inv3 != null) tampilMatriks(inv3);
                    else System.out.println("Tidak memiliki invers!");

                    System.out.println("\n3. HASIL OBE:");
                    if (det3 != 0) {
                        obe3(aug3);
                    } else {
                        System.out.println("Tidak bisa diselesaikan (determinan = 0)");
                    }

                    System.out.println("---------------------------------");
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