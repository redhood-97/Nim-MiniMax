package com.NimGame.Nim;

import com.NimGame.Nim.Game;

public class MiniMaxSearch<STATE, ACTION, PLAYER> implements DecisionSearch<STATE, ACTION> {

    private Game<STATE, ACTION, PLAYER> game;
    private int expandedNodes;

    public static <STATE, ACTION, PLAYER> MiniMaxSearch<STATE, ACTION, PLAYER> createFor(
            Game<STATE, ACTION, PLAYER> game) {
        return new MiniMaxSearch<STATE, ACTION, PLAYER>(game);
    }


    public MiniMaxSearch(Game<STATE, ACTION, PLAYER> game) {
        this.game = game;
    }

    @Override
    public ACTION makeDecision(STATE state) {
        expandedNodes = 0;
        ACTION result = null;
        double resultValue = Double.NEGATIVE_INFINITY;
        PLAYER player = game.getPlayer(state);
        for (ACTION action : game.getActions(state)) {
            double value = minValue(game.getResult(state, action),
                    player);
            if (value > resultValue) {
                result = action;
                resultValue = value;
            }
        }
        return result;
    }

    public double maxValue(STATE state, PLAYER player) { // returns an utility
        // value
        expandedNodes++;
        if (game.isTerminal(state))
            return game.getUtility(state, player);
        double value = Double.NEGATIVE_INFINITY;
        for (ACTION action : game.getActions(state))
            value = Math.max(value,
                    minValue(game.getResult(state, action),
                            player));
        return value;
    }

    public double minValue(STATE state, PLAYER player) { // returns an utility
        // value
        expandedNodes++;
        if (game.isTerminal(state))
            return game.getUtility(state, player);
        double value = Double.POSITIVE_INFINITY;
        for (ACTION action : game.getActions(state))
            value = Math.min(value,
                    maxValue(game.getResult(state, action),
                            player));
        return value;
    }

    @Override
    public Metrics getMetrics() {
        Metrics result = new Metrics();
        result.set("expandedNodes", expandedNodes);
        return result;
    }
}
