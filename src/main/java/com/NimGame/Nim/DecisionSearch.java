package com.NimGame.Nim;
import com.NimGame.Nim.Metrics;

public interface DecisionSearch<STATE, ACTION> {

    ACTION makeDecision(STATE state);
    Metrics getMetrics();
}
