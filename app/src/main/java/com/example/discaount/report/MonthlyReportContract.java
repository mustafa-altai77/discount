package com.example.discaount.report;


import com.example.discaount.BasePresenter;
import com.example.discaount.BaseView;
import com.example.discaount.History;

import java.util.List;

/**
 * Created by gautam on 13/07/17.
 */

public interface MonthlyReportContract {

    interface View extends BaseView<Presenter> {

        void setPresenter(Presenter presenter);

        void setLoadingIndicator(boolean active);

        void showHistoryList(List<History> historyList);

        void showLoadingError();

        void showNoHistory();

        void showTakenFilterLabel();

        void showIgnoredFilterLabel();

        void showAllFilterLabel();

        void showNoTakenHistory();

        void showNoIgnoredHistory();

        boolean isActive();

        void showFilteringPopUpMenu();

    }

    interface Presenter extends BasePresenter {

        void start();

        void loadHistory(boolean showLoading);

        void setFiltering(FilterType filterType);

        FilterType getFilterType();
    }
}
