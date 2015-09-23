package pl.grzeslowski;

import org.jnativehook.GlobalScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    static {
        System.setProperty("java.util.logging.config.file", Main.class.getResource("/javaLogging.properties").getPath());
    }

    public static void main(String[] params) throws Exception {

        KeyListener listener = new KeyListener();

        Observable.OnSubscribe<String> subscribe = listener::addSubscriber;

        Observable<String> observable = Observable.create(subscribe);

        observable
                .map(s -> s.equals("Space") ? " " : s)
                .map(s -> s.equals("Backspace") ? "ᐊ" : s)
                .map(s -> s.equals("Right Shift") ? "ᐃ" : s)
                .map(s -> s.equals("Left Shift") ? "ᐃ" : s)
                .map(s -> s.equals("Comma") ? "," : s)
                .map(s -> s.equals("Minus") ? "-" : s)
                .map(s -> s.equals("Equals") ? "=" : s)
                .map(s -> s.equals("Caps Lock") ? "" : s)
                .map(s -> s.equals("Tab") ? "ᐅ" : s)
                .map(s -> s.equals("Escape") ? "✕" : s)
                .map(s -> s.equals("Period") ? "." : s)
                .map(s -> s.equals("Slash") ? "/" : s)
                .map(s -> s.equals("Semicolon") ? ";" : s)
                .map(s -> s.equals("Up") ? "↑" : s)
                .map(s -> s.equals("Down") ? "↓" : s)
                .map(s -> s.equals("Right") ? "→" : s)
                .map(s -> s.equals("Left") ? "←" : s)
                .buffer(5, TimeUnit.SECONDS)
                .flatMap((it) -> Observable.just(it.stream().reduce((l, r) -> l + r)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap((String it) -> Observable.from(it.split("Enter")))
                .filter(s -> s != null && !s.isEmpty())
                .subscribe(new LogObserver());

        // registering hook
        logger.info("Starting hooking...");
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(listener);
    }
}
