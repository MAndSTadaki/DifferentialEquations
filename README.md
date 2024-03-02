# 微分方程式によるモデリング

## packages
- `rungeKutta`: Runge Kutta法
    - `DifferentialEquations.java`:連立1次微分方程式を定義するためのインターフェース
    - `RungeKutta.java`: 4次のRunge Kutta法
- `simpleExamples`: 簡単な振動子の例
    - `HarmonicOscillator0.java`: 調和振動子の例。Lambda式を使わない
    - `HarmonicOscillator.java`: 調和振動子の例。Lambda式を使う
    - `NonlinearOscillator.java`: 単振り子
    - `HarmonicOscillatorWithExternalForce.java`: 周期外力のある調和振動子
- `coupledOscillators`: 連成振動
    - `CoupledOscillators.java`