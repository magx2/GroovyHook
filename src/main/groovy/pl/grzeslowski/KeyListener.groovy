package pl.grzeslowski

import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import rx.Subscriber

class KeyListener implements NativeKeyListener {
	Set<Subscriber<? super String>> subscribers = [] as HashSet

	def addSubscriber(Subscriber<? super String> subscriber) {
		subscribers.add subscriber
	}

	@Override
	void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
		final key = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode())
		subscribers.each { it.onNext(key) }
	}

	@Override
	void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
	}

	@Override
	void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
	}
}
