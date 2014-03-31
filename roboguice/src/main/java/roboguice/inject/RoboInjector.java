package roboguice.inject;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.inject.Injector;

public interface RoboInjector extends Injector {
    void injectViewMembers(Activity activity);
    void injectViewMembers(Fragment fragment);
    void injectViewMembers(View view);
    void injectMembersWithoutViews(Object instance);
}
