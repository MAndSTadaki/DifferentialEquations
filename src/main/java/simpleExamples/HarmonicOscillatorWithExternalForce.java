package simpleExamples;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.function.DoubleFunction;
import rungeKutta.DifferentialEquations;
import rungeKutta.RungeKutta;

/**
 * 調和振動子
 *
 * @author tadaki
 */
public class HarmonicOscillatorWithExternalForce {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //初期値：変位1、速度0
        double x0 = 1.;
        double v0 = 0.;
        double omega = 1.;// k/mに相当
//        double r = 1.01;
        double beta = 0.;
        double f = .1;
        double h = 1.e-02;

        double rArray[] = {1.1, 1.01};

        for (double r : rArray) {
            double gamma = Math.sqrt(omega) * r;
            DoubleFunction<Double> exForce = (double t) -> {
                return f * Math.cos(gamma * t + beta);
            };

            DifferentialEquations eq = (double t, double[] yy) -> {
                double dy[] = new double[2];
                dy[0] = yy[1];// dx/dt = v
                // dv/dt = - omega^2 x + exF(t)
                dy[1] = -omega * omega * yy[0] + exForce.apply(t);
                return dy;
            };
            //出力ファイル名指定：クラス名.txt
            String filename
                    = HarmonicOscillatorWithExternalForce.class.getSimpleName()
                    + "-" + String.valueOf(r) + ".txt";
            double t = 0;
            double tmax = 200;
            //出力を開く
            try (PrintStream out = new PrintStream(new FileOutputStream(filename))) {
                double yy[] = {x0, v0};
                while (t < tmax) {
                    yy = RungeKutta.rk4(t, yy, h, eq);
                    t += h;
                    //tとyy[0]をスペース区切りで出力
                    out.println(t + " " + yy[0]);
                }
            }
        }

    }
}
