package simpleExamples;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import rungeKutta.*;

/**
 * 非調和振動
 *
 * @author tadaki
 */
public class NonlinearOscillator {

    static public void main(String args[]) throws IOException {
        double t = 0;
        double tmax = 8 * Math.PI;
        double h = 1.e-02;
        double kappa = 1.;
        //微分方程式の定義
        DifferentialEquations eq = (x, y) -> {
            double dy[] = new double[y.length];
            dy[0] = y[1];// dx/dt = v
            dy[1] = -kappa * kappa * Math.sin(y[0]);// dv/dt = - kappa*x
            return dy;
        };
        //出力ファイル名指定：クラス名.txt
        String filename = NonlinearOscillator.class.getSimpleName() + ".txt";
        //出力を開く
        try ( PrintStream out = new PrintStream(new FileOutputStream(filename))) {
            double yy[] = {1., 0.};//初期値：変位1、速度0
            while (t < tmax) {
                yy = RungeKutta.rk4(t, yy, h, eq);
                t += h;
                out.println(t + " " + yy[0]);
            }
        }
    }
}
