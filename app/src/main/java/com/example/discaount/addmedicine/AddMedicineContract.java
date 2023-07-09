package com.example.discaount.addmedicine;



import com.example.discaount.BasePresenter;
import com.example.discaount.BaseView;
import com.example.discaount.local.MedicineAlarm;
import com.example.discaount.local.Pills;

import java.util.List;

/**
 * Created by gautam on 12/07/17.
 */

public interface AddMedicineContract {

    interface View extends BaseView<Presenter> {

        void showEmptyMedicineError();

        void showMedicineList();

        boolean isActive();

    }

    interface  Presenter extends BasePresenter {


        void saveMedicine(MedicineAlarm alarm, Pills pills);


        boolean isDataMissing();

        boolean isMedicineExits(String pillName);

        long addPills(com.example.discaount.local.Pills pills);

        Pills getPillsByName(String pillName);

        List<MedicineAlarm> getMedicineByPillName(String pillName);

        List<Long> tempIds();

        void deleteMedicineAlarm(long alarmId);

    }
}
