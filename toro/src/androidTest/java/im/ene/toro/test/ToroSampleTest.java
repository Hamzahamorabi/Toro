/*
 * Copyright 2017 eneim@Eneim Labs, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.ene.toro.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import im.ene.toro.PlaylistHelper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class) public class ToroSampleTest {

  @Rule public ActivityTestRule<ToroTestActivity> activityTestRule =
      new ActivityTestRule<>(ToroTestActivity.class);

  @Test public void testRegisterRecyclerView() throws Throwable {
    final ToroTestActivity activity = activityTestRule.getActivity();
    activityTestRule.runOnUiThread(new Runnable() {
      @Override public void run() {
        activity.setContentView(R.layout.activity_test);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recycler_view);
        Assert.assertNotNull(recyclerView);

        TestAdapter adapter = new TestAdapter();
        recyclerView.setAdapter(adapter);

        PlaylistHelper helper = new PlaylistHelper(adapter);
        Assert.assertNotNull(helper.getStrategy());
      }
    });
  }
}