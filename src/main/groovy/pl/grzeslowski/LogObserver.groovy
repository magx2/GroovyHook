package pl.grzeslowski

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import rx.Subscriber

class LogObserver extends Subscriber<String> {
	static Logger logger = LoggerFactory.getLogger(LogObserver.class);

	@Override
	void onCompleted() {
		logger.info("onCompleted")
	}

	@Override
	void onError(Throwable e) {
		logger.error("onError", e)

	}

	@Override
	void onNext(String s) {
		logger.info(s)
	}
}
