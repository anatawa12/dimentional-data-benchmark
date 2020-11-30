package com.anatawa12;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;

public class DoubleVectorTest {
    @State(Scope.Thread)
    public static class Input {
        public Input() {

        }

        @Setup(Level.Iteration)
        public void setUp() {
            vectors = new Vec3D[count];
            doubles3 = new double[count * 3];
            doubles4 = new double[count * 4];

            for (int i = 0; i < count; i++) {
                double x = getNextDouble();
                double y = getNextDouble();
                double z = getNextDouble();
                vectors[i] = new Vec3D(x, y, z);

                doubles3[i * 3 + 0] = x;
                doubles3[i * 3 + 1] = y;
                doubles3[i * 3 + 2] = z;

                doubles4[i * 4 + 0] = x;
                doubles4[i * 4 + 1] = y;
                doubles4[i * 4 + 2] = z;
            }
        }

        private double getNextDouble() {
            while (true) {
                double value = Double.longBitsToDouble(random.nextLong());
                if (Double.isInfinite(value) || Double.isNaN(value))
                    continue;
                return value;
            }
        }

        public Random random = new Random();
        public Vec3D[] vectors;
        public double[] doubles3;
        public double[] doubles4;
    }

    public static class Vec3D {
        public final double x;
        public final double y;
        public final double z;

        public Vec3D(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    @Benchmark
    public static void randomGetByVectors(Blackhole hole, Input input) {
        Vec3D[] vecotors = input.vectors;
        for (int s = 0; s < count; s++) {
            int i = input.random.nextInt(count);
            double x = vecotors[i].x;
            double y = vecotors[i].y;
            double z = vecotors[i].z;
            hole.consume(x);
            hole.consume(y);
            hole.consume(z);
        }
    }

    @Benchmark
    public static void sequentialGetByVectors(Blackhole hole, Input input) {
        Vec3D[] vecotors = input.vectors;
        for (int i = 0; i < count; i++) {

            double x = vecotors[i].x;
            double y = vecotors[i].y;
            double z = vecotors[i].z;
            hole.consume(x);
            hole.consume(y);
            hole.consume(z);
        }
    }

    @Benchmark
    public static void randomGetByDoubles3(Blackhole hole, Input input) {
        double[] doubles3 = input.doubles3;
        for (int s = 0; s < count; s++) {
            int i = input.random.nextInt(count);
            double x = doubles3[i * 3 + 0];
            double y = doubles3[i * 3 + 1];
            double z = doubles3[i * 3 + 2];
            hole.consume(x);
            hole.consume(y);
            hole.consume(z);
        }
    }

    @Benchmark
    public static void sequentialGetByDoubles3(Blackhole hole, Input input) {
        double[] doubles3 = input.doubles3;
        for (int i = 0; i < count; i++) {

            double x = doubles3[i * 3 + 0];
            double y = doubles3[i * 3 + 1];
            double z = doubles3[i * 3 + 2];
            hole.consume(x);
            hole.consume(y);
            hole.consume(z);
        }
    }

    @Benchmark
    public static void randomGetByDoubles4(Blackhole hole, Input input) {
        double[] doubles4 = input.doubles4;
        for (int s = 0; s < count; s++) {
            int i = input.random.nextInt(count);
            double x = doubles4[i * 4 + 0];
            double y = doubles4[i * 4 + 1];
            double z = doubles4[i * 4 + 2];
            hole.consume(x);
            hole.consume(y);
            hole.consume(z);
        }
    }

    @Benchmark
    public static void sequentialGetByDoubles4(Blackhole hole, Input input) {
        double[] doubles4 = input.doubles4;
        for (int i = 0; i < count; i++) {

            double x = doubles4[i * 4 + 0];
            double y = doubles4[i * 4 + 1];
            double z = doubles4[i * 4 + 2];
            hole.consume(x);
            hole.consume(y);
            hole.consume(z);
        }
    }

    public static int count = 0x1_000;
}
