package coupledOscillators;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import rungeKutta.*;

/**
 * 連成振動
 */
public class CoupledOscillators {

    static public void main(String args[]) throws IOException {
        double t = 0;
        double tmax = 8 * Math.PI;
        double h = 1.e-02;
        double omega = 1.;
        double b = 10.;
        int numOscillators = 3;
        //微分方程式の定義
        //i 番目の位置はy[2*i]、速度はy[2*i+1]
        DifferentialEquations eq = (x, y) -> {
            double dy[] = new double[y.length];
            //0 番の粒子
            dy[0] = y[1];
            dy[1] = -omega * omega * (2 * y[0] - y[2]);
            //1番からn-2番の粒子
            for (int i = 1; i < numOscillators - 1; i++) {
                int j = 2 * i;
                dy[j] = y[j + 1];
                dy[j + 1] = -omega * omega * (-y[j - 2] + 2 * y[j] - y[j + 2]);
            }
            //n-1番の粒子
            int j = 2 * (numOscillators - 1);
            dy[j] = y[j + 1];
            dy[j + 1] = -omega * omega * (-y[j - 2] + 2 * y[j]);
            return dy;
        };
        //出力ファイル名指定：クラス名.txt
        String filename = CoupledOscillators.class.getSimpleName() + ".txt";
        String filename2 = CoupledOscillators.class.getSimpleName() + "-base.txt";
        //出力を開く
        try ( PrintStream out2 = new PrintStream(new FileOutputStream(filename2))) {
            try ( PrintStream out = new PrintStream(new FileOutputStream(filename))) {
                double yy[] = {1., 0., 2., 0., -1, 0.};//初期値：
                while (t < tmax) {
                    yy = RungeKutta.rk4(t, yy, h, eq);
                    t += h;
                    //各粒子の振動を出力
                    out.println(t + " " + (yy[0] + b) + " "
                            + (yy[2] + 2 * b) + " " + (yy[4] + 3 * b));
                    //各調和振動を出力
                    out2.println(t + "  "
                            + (-Math.sqrt(2) * (yy[0] - yy[4]) / 2 + b) + " "
                            + ((yy[0] - Math.sqrt(2) * yy[2] + yy[4]) / 2 + 2 * b) + " "
                            + ((yy[0] + Math.sqrt(2) * yy[2] + yy[4]) / 2 + 3 * b));
                }
            }
        }
    }
}
