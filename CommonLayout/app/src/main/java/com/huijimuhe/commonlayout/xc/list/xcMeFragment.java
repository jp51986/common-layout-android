package com.huijimuhe.commonlayout.xc.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Copyright (C) 2016 Huijimuhe Technologies. All rights reserved.
 * <p>
 * Contact: 20903213@qq.com Zengweizhou
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class xcMeFragment extends Fragment {
    public static xcMeFragment newInstance() {
        xcMeFragment fragment = new xcMeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
