package roboguice.view;

import roboguice.RoboGuice;
import roboguice.activity.event.*;
import roboguice.event.EventManager;
import roboguice.inject.ContentViewListener;
import roboguice.inject.RoboInjector;
import roboguice.util.RoboContext;
import android.util.Log;
import android.util.AttributeSet;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link RoboActivity} extends from {@link Activity} to provide dynamic
 * injection of collaborators, using Google Guice.<br />
 * <br />
 * Your own activities that usually extend from {@link Activity} should now
 * extend from {@link RoboActivity}.<br />
 * <br />
 * If your activities extend from subclasses of {@link Activity} provided by the
 * Android SDK, we provided Guice versions as well for the most used : see
 * {@link RoboExpandableListActivity}, {@link RoboListActivity}, and other
 * classes located in package <strong>roboguice.activity</strong>.<br />
 * <br />
 * If we didn't provide what you need, you have two options : either post an
 * issue on <a href="http://code.google.com/p/roboguice/issues/list">the bug
 * tracker</a>, or implement it yourself. Have a look at the source code of this
 * class ({@link RoboActivity}), you won't have to write that much changes. And
 * of course, you are welcome to contribute and send your implementations to the
 * RoboGuice project.<br />
 * <br />
 * Please be aware that collaborators are not injected into this until you call
 * {@link #setContentView(int)} (calling any of the overloads of this methods
 * will work).<br />
 * <br />
 * You can have access to the Guice {@link Injector} at any time, by calling
 * {@link #getInjector()}.<br />
 * However, you will not have access to ContextSingleton scoped beans until
 * {@link #onCreate(Bundle)} is called. <br />
 * <br />
 * 
 */
public class RoboRelativeLayout extends android.widget.RelativeLayout implements RoboContext {
   protected EventManager eventManager;
   protected HashMap<Key<?>,Object> scopedObjects = new HashMap<Key<?>, Object>();

   @Inject ContentViewListener ignored; // BUG find a better place to put this

   public RoboRelativeLayout (Context context) {
      super(context);
      init(context);
   }

   public RoboRelativeLayout (Context context, AttributeSet attrs) {
      super(context, attrs);
      init(context);
   }
   /*
   public RoboRelativeLayout (Context context, AttributeSet attrs, int defStyle) {
      super(context, attrs, defStyle);
      init(context);
   }
   */
   private void init(Context context) {
      final RoboInjector injector = RoboGuice.getInjector(getContext());
      eventManager = injector.getInstance(EventManager.class);
      injector.injectMembersWithoutViews(this);
      //eventManager.fire(new OnCreateEvent(savedInstanceState));
   }

   @Override
   protected void onFinishInflate() {
      super.onFinishInflate();
      RoboGuice.getInjector(getContext()).injectViewMembers(this);
      //eventManager.fire(new OnContentChangedEvent());
   }

   @Override
   public Map<Key<?>, Object> getScopedObjectMap() {
      return scopedObjects;
   }
}
