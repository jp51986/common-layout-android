package com.huijimuhe.commonlayout.xc.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huijimuhe.commonlayout.AppContext;
import com.huijimuhe.commonlayout.R;
import com.huijimuhe.commonlayout.adapter.base.AbstractRenderAdapter;
import com.huijimuhe.commonlayout.adapter.xcCommentAdapter;
import com.huijimuhe.commonlayout.adapter.xcArticleSectionAdapter;
import com.huijimuhe.commonlayout.data.xc.xcComment;
import com.huijimuhe.commonlayout.data.xc.xcDetailResponse;
import com.huijimuhe.commonlayout.data.xc.xcArticleSection;
import com.huijimuhe.commonlayout.utils.ViewUtility;
import com.huijimuhe.commonlayout.widget.NoScrollRecyclerView;
import com.huijimuhe.commonlayout.xc.list.xcFXListFragment;

import java.util.ArrayList;

/**
 * Copyright (C) 2016 Huijimuhe Technologies. All rights reserved.
 * <p/>
 * Contact: 20903213@qq.com Zengweizhou
 * <p/>
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
public class xcDetailContainerFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RelativeLayout mCommentEditView;
    protected AbstractRenderAdapter mAdapter;
    protected LinearLayoutManager mLayoutManager;

    private HeaderViewWrapper mHeaderView;
    private xcDetailResponse mResponse;

    public static xcDetailContainerFragment newInstance() {
        xcDetailContainerFragment fragment = new xcDetailContainerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.xc_fragment_detail_container, null);


        //recycler view
        mRecyclerView = (RecyclerView) v.findViewById(R.id.article_comment_list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        //set adapter
        mAdapter = getRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbstractRenderAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

            }
        });
        mAdapter.setOnItemFunctionClickListener(new AbstractRenderAdapter.onItemFunctionClickListener() {
            @Override
            public void onClick(View view, int postion, int type) {

            }
        });

        //评论
        mCommentEditView = (RelativeLayout) v.findViewById(R.id.article_edit_container);
        v.findViewById(R.id.article_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCommentEditView.setVisibility(View.VISIBLE);
                showKeyBoard();
            }
        });
        mCommentEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                hiddenKeyBoard(view);
            }
        });
        addHeaderView(v);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public void load(xcDetailResponse data) {
        this.mResponse = data;
        mAdapter.replace(mResponse.getComments());
        mHeaderView.init(mResponse);
    }

    public AbstractRenderAdapter getRecyclerAdapter() {
        return new xcCommentAdapter(new ArrayList<xcComment>());
    }

    public void addHeaderView(View root) {
        mHeaderView = new HeaderViewWrapper(getActivity());
        mHeaderView.setOnHeaderViewWrapperClickListener(new HeaderViewWrapperClickListener() {
            @Override
            public void onTopClick(View view) {

            }

            @Override
            public void onSaleClick(View view) {

            }

            @Override
            public void onArticleClick(View view) {
                getActivity().startActivity(xcDetailActivity.newIntent());
            }
        });
        mAdapter.setHeaderView(mHeaderView.getRoot());
    }

    private class HeaderViewWrapper {
        /**
         * Top Image
         */
        private ImageView ivTop;
        private TextView tvTitle;
        private TextView tvSubTitle;
        /**
         * Author Intro
         */
        private ImageView ivAvatar;
        private TextView tvName;
        private TextView tvUserIntro;
        private TextView tvArticleIntro;
        /**
         * recommend
         */
        private ImageView ivRecommendImg;
        private TextView tvRecommendTitle;
        private TextView tvRecommendBody;
        /**
         * section body list
         */
        private NoScrollRecyclerView bodyList;
        private xcArticleSectionAdapter sectionAdapter;
        private HeaderViewWrapperClickListener l;
        private LinearLayout root;

        public HeaderViewWrapper(Context context) {
            root = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xc_listheader_detail, mRecyclerView, false);
            initUI(root, context);
        }

        public void setOnHeaderViewWrapperClickListener(HeaderViewWrapperClickListener l) {
            this.l = l;
        }

        public View getRoot() {
            return root;
        }

        public void initUI(View view, Context context) {

            /**Top Image*/
            tvTitle = ViewUtility.findViewById(view, R.id.article_header_title);
            tvSubTitle = ViewUtility.findViewById(view, R.id.article_header_sub_title);
            ivTop = ViewUtility.findViewById(view, R.id.article_header_image);
            ivTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    l.onTopClick(view);
                }
            });

            /**Author Intro*/
            ivAvatar = ViewUtility.findViewById(view, R.id.article_author_header);
            tvName = ViewUtility.findViewById(view, R.id.article_author_name);
            tvUserIntro = ViewUtility.findViewById(view, R.id.article_author_des);
            tvArticleIntro = ViewUtility.findViewById(view, R.id.article_intro);

            /**recommend*/
            tvRecommendTitle = ViewUtility.findViewById(view, R.id.card_advertise_title);
            tvRecommendBody = ViewUtility.findViewById(view, R.id.card_advertise_desc);
            ivRecommendImg = ViewUtility.findViewById(view, R.id.card_advertise_icon);

            /**Article Body*/
            bodyList = ViewUtility.findViewById(view, R.id.article_content_list);
            LinearLayoutManager saleManager = new LinearLayoutManager(getActivity());
            saleManager.setOrientation(LinearLayoutManager.VERTICAL);
            bodyList.setLayoutManager(saleManager);

            sectionAdapter = new xcArticleSectionAdapter(new ArrayList<xcArticleSection>());
            sectionAdapter.setOnItemClickListener(new AbstractRenderAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {

                }
            });
            bodyList.setAdapter(sectionAdapter);

            /**store poi*/
            //Fake your own
        }

        public void init(xcDetailResponse response) {
            /**Top Image*/
            Glide.with(AppContext.getInstance().getApplicationContext()).load(response.getArticle().getThumbnail()).into(ivTop);
            tvTitle.setText(response.getArticle().getTitle());
            tvSubTitle.setText("7272次浏览_1222人收藏");
            /**Author Intro*/
            Glide.with(AppContext.getInstance().getApplicationContext()).load(response.getArticle().getAuthor_info().getProfile_image()).into(ivAvatar);
            tvName.setText(response.getArticle().getAuthor_account().getNickname());
            tvUserIntro.setText(response.getArticle().getAuthor_account().getIntro());
            tvArticleIntro.setText(response.getArticle().getIntro());
            /**recommend*/
            Glide.with(AppContext.getInstance().getApplicationContext()).load(response.getAds().getImage()).into(ivRecommendImg);
            tvRecommendBody.setText(response.getAds().getDesc());
            tvRecommendTitle.setText(response.getAds().getTitle());

            /**Article Body*/
            sectionAdapter.replace(response.getArticle().getBody());
        }
    }

    public interface HeaderViewWrapperClickListener {
        void onTopClick(View view);

        void onSaleClick(View view);

        void onArticleClick(View view);
    }

    protected void hiddenKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    protected void showKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }
}
