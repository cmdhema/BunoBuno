package kjw.kr.bunobuno;

/**
 * Created by kjwook on 2017. 1. 20..
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void showSuccessfullySavedMessage();
}
