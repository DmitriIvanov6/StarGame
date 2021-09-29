package ru.gb;

import com.badlogic.gdx.Game;

import ru.gb.screen.MenuScreen;

public class Stargame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

}
