package com.numbers.currencies.Network;
import com.numbers.currencies.MainController;

import dagger.Component;

@Component(modules = NetworkService.class)
public interface NetworkComponent {

    void inject(MainController mainController);
}
