package com.example.discaount.medicine;
import android.content.Context;

import com.example.discaount.BasePresenter;
import com.example.discaount.BaseView;
import com.example.discaount.local.MedicineAlarm;
import java.util.List;

/**
 * Created by gautam on 13/07/17.
 */
public interface MedicineContract{
    interface View extends BaseView<Presenter> {
        void setPresenter(Presenter presenter);
        void showLoadingIndicator(boolean active);
        void showMedicineList(List<MedicineAlarm> medicineAlarmList);
        void showAddMedicine();
        void showMedicineDetails(long medId, String medName);
        void showLoadingMedicineError();
        void showNoMedicine();
        void showSuccessfullySavedMessage();
        void  showMedicineDeletedSuccessfully();
        boolean isActive();
    }
    interface Presenter extends BasePresenter {

        void start();

        void onStart(int day);

        void reload(int day);

        void result(int requestCode, int resultCode);

        void loadMedicinesByDay(int day, boolean showIndicator);

        void deleteMedicineAlarm(MedicineAlarm medicineAlarm, Context context);

        void addNewMedicine();

    }
}
