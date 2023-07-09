package com.example.discaount.addmedicine;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.example.discaount.R;

import com.example.discaount.alarm.ReminderActivity;
import com.example.discaount.alarm.ReminderFragment;
import com.example.discaount.local.MedicineAlarm;
import com.example.discaount.local.Pills;
import com.example.discaount.utils.ToastMessage;
import com.example.discaount.views.DayViewCheckBox;
import com.example.discaount.views.RobotoBoldTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by gautam on 12/07/17.
 */

public class AddMedicineFragment extends Fragment implements AddMedicineContract.View {
    int counter=0;
    public static final String ARGUMENT_EDIT_MEDICINE_ID = "ARGUMENT_EDIT_MEDICINE_ID";

    public static final String ARGUMENT_EDIT_MEDICINE_NAME = "ARGUMENT_EDIT_MEDICINE_NAME";

    @BindView(R.id.edit_med_name)
    EditText editMedName;

    @BindView(R.id.every_day)
    AppCompatCheckBox everyDay;

    @BindView(R.id.dv_sunday)
    DayViewCheckBox dvSunday;

    @BindView(R.id.dv_monday)
    DayViewCheckBox dvMonday;

    @BindView(R.id.dv_tuesday)
    DayViewCheckBox dvTuesday;

    @BindView(R.id.dv_wednesday)
    DayViewCheckBox dvWednesday;

    @BindView(R.id.dv_thursday)
    DayViewCheckBox dvThursday;

    @BindView(R.id.dv_friday)
    DayViewCheckBox dvFriday;

    @BindView(R.id.dv_saturday)
    DayViewCheckBox dvSaturday;

    @BindView(R.id.checkbox_layout)
    LinearLayout checkboxLayout;

    @BindView(R.id.tv_medicine_time)
    RobotoBoldTextView tvMedicineTime;

    @BindView(R.id.tv_dose_quantity)
    EditText tvDoseQuantity;

    @BindView(R.id.spinner_dose_units)
    AppCompatSpinner spinnerDoseUnits;

    private List<String> doseUnitList;

    private boolean[] dayOfWeekList = new boolean[7];

    private int hour, minute;

    Unbinder unbinder;

    private AddMedicineContract.Presenter mPresenter;

    private View rootView;

    private String doseUnit;


    static AddMedicineFragment newInstance() {
        Bundle args = new Bundle();
        AddMedicineFragment fragment = new AddMedicineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = requireActivity().findViewById(R.id.fab_edit_task_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(setClickListener);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_medicine, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setCurrentTime();
        setSpinnerDoseUnits();
        LinearLayout ll = rootView.findViewById(R.id.checkbox_layout);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View v = ll.getChildAt(i);
            ((DayViewCheckBox) v).setChecked(true);
            onCheckboxClicked(v);
        }
        counter=7;
        return rootView;
    }

    @Override
    public void setPresenter(AddMedicineContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showEmptyMedicineError() {
        // Snackbar.make(mTitle, getString(R.string.empty_task_message), Snackbar.LENGTH_LONG).show();
    }

    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public void showMedicineList() {
        Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.every_day, R.id.dv_monday, R.id.dv_tuesday, R.id.dv_wednesday,
            R.id.dv_thursday, R.id.dv_friday, R.id.dv_saturday, R.id.dv_sunday})
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        /** Checking which checkbox was clicked */
        switch (view.getId()) {
            case R.id.dv_sunday:
                if (checked) {
                    dayOfWeekList[1] = true;
                    counter=counter+1;
                } else {
                    counter=counter-1;
                    dayOfWeekList[1] = false;
                    everyDay.setChecked(false);
                }
                break;
            case R.id.dv_monday:
                if (checked) {
                    dayOfWeekList[2] = true;
                    counter=counter+1;
                } else {
                    counter=counter-1;
                    dayOfWeekList[2] = false;
                    everyDay.setChecked(false);
                }
                break;
            case R.id.dv_tuesday:
                if (checked) {
                    counter=counter+1;
                    dayOfWeekList[3] = true;
                } else {
                    counter=counter-1;
                    dayOfWeekList[3] = false;
                    everyDay.setChecked(false);
                }
                break;
            case R.id.dv_wednesday:
                if (checked) {
                    counter=counter+1;
                    dayOfWeekList[4] = true;
                } else {
                    counter=counter-1;
                    dayOfWeekList[4] = false;
                    everyDay.setChecked(false);
                }
                break;
            case R.id.dv_thursday:
                counter=counter+1;
                if (checked) {
                    dayOfWeekList[5] = true;
                } else {
                    counter=counter-1;
                    dayOfWeekList[5] = false;
                    everyDay.setChecked(false);
                }
                break;
            case R.id.dv_friday:
                if (checked) {
                    counter=counter+1;
                    dayOfWeekList[6] = true;
                } else {
                    counter=counter-1;
                    dayOfWeekList[6] = false;
                    everyDay.setChecked(false);
                }
                break;
            case R.id.dv_saturday:
                if (checked) {
                    counter=counter+1;
                    dayOfWeekList[0] = true;
                } else {
                    counter=counter-1;
                    dayOfWeekList[0] = false;
                    everyDay.setChecked(false);
                }
                break;
            case R.id.every_day:
                LinearLayout ll = rootView.findViewById(R.id.checkbox_layout);
                for (int i = 0; i < ll.getChildCount(); i++) {
                    View v = ll.getChildAt(i);
                    ((DayViewCheckBox) v).setChecked(checked);
                    onCheckboxClicked(v);
                }
                counter=7;
                break;

        }

    }

    @OnClick(R.id.tv_medicine_time)
    void onMedicineTimeClick() {
        showTimePicker();
    }

    private void showTimePicker() {
        Calendar mCurrentTime = Calendar.getInstance();
        hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                tvMedicineTime.setText(String.format(Locale.getDefault(), "%d:%d", selectedHour, selectedMinute));
            }
        }, hour, minute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void setCurrentTime() {
        Calendar mCurrentTime = Calendar.getInstance();
        hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mCurrentTime.get(Calendar.MINUTE);

        tvMedicineTime.setText(String.format(Locale.getDefault(), "%d:%d", hour, minute));
    }

    private void setSpinnerDoseUnits() {
        doseUnitList = Arrays.asList(getResources().getStringArray(R.array.medications_shape_array));
        @SuppressLint("UseRequireInsteadOfGet") ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_dropdown_item_1line, doseUnitList);
        spinnerDoseUnits.setAdapter(adapter);
    }

    @OnItemSelected(R.id.spinner_dose_units)
    void onSpinnerItemSelected(int position) {
        if (doseUnitList == null || doseUnitList.isEmpty()) {
            return;
        }

        doseUnit = doseUnitList.get(position);
        Log.d("TAG", doseUnit);
    }

    private View.OnClickListener setClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int checkBoxCounter = 0;
            String pill_name = editMedName.getText().toString();
            if (pill_name.isEmpty() || pill_name.length() < 5) {
                editMedName.setError(getResources().getString(R.string.error));
                return;
            }
            if (counter<2)
            {
                ToastMessage message = new ToastMessage(getActivity());
                message.messageError(getResources().getString(R.string.choose_two_day), true);
            return;
            }
            if (!dvFriday.isChecked() && !dvThursday.isChecked() && !dvWednesday.isChecked() &&
                    !dvTuesday.isChecked() && !dvMonday.isChecked() && !dvSunday.isChecked() && !dvSaturday.isChecked()) {
                ToastMessage message = new ToastMessage(getActivity());
                message.messageError(getResources().getString(R.string.choose_day), true);
                return;
            }
            Toast.makeText(getActivity(), "" + dayOfWeekList.toString() + " " + checkBoxCounter, Toast.LENGTH_SHORT).show();


            String doseQuantity = tvDoseQuantity.getText().toString();

            Calendar takeTime = Calendar.getInstance();
            Date date = takeTime.getTime();
            String dateString = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date);

            /** Updating model */
            MedicineAlarm alarm = new MedicineAlarm();
            int alarmId = new Random().nextInt(100);

            /** If Pill does not already exist */
            if (!mPresenter.isMedicineExits(pill_name)) {
                Pills pill = new Pills();
                pill.setPillName(pill_name);
                alarm.setDateString(dateString);
                alarm.setHour(hour);
                alarm.setMinute(minute);
                alarm.setPillName(pill_name);
                alarm.setDayOfWeek(dayOfWeekList);
                alarm.setDoseUnit(doseUnit);
                alarm.setDoseQuantity(doseQuantity);
                alarm.setAlarmId(alarmId);
                pill.addAlarm(alarm);
                long pillId = mPresenter.addPills(pill);
                pill.setPillId(pillId);
                mPresenter.saveMedicine(alarm, pill);
            } else { // If Pill already exists
                Pills pill = mPresenter.getPillsByName(pill_name);
                alarm.setDateString(dateString);
                alarm.setHour(hour);
                alarm.setMinute(minute);
                alarm.setPillName(pill_name);
                alarm.setDayOfWeek(dayOfWeekList);
                alarm.setDoseUnit(doseUnit);
                alarm.setDoseQuantity(doseQuantity);
                alarm.setAlarmId(alarmId);
                pill.addAlarm(alarm);
                mPresenter.saveMedicine(alarm, pill);
            }

            List<Long> ids = new LinkedList<>();
            try {
                List<MedicineAlarm> alarms = mPresenter.getMedicineByPillName(pill_name);
                for (MedicineAlarm tempAlarm : alarms) {
                    if (tempAlarm.getHour() == hour && tempAlarm.getMinute() == minute) {
                        ids = tempAlarm.getIds();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 7; i++) {
                if (dayOfWeekList[i] && pill_name.length() != 0) {

                    int dayOfWeek = i + 1;
                    long _id = ids.get(checkBoxCounter);
                    int id = (int) _id;
                    checkBoxCounter++;

                    /** This intent invokes the activity ReminderActivity, which in turn opens the AlertAlarm window */
                    Intent intent = new Intent(getActivity(), ReminderActivity.class);
                    intent.putExtra(ReminderFragment.EXTRA_ID, _id);

                    PendingIntent operation = PendingIntent.getActivity(getActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    /** Getting a reference to the System Service ALARM_SERVICE */
                    @SuppressLint("UseRequireInsteadOfGet") AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(getActivity()).getSystemService(ALARM_SERVICE);

                    /** Creating a calendar object corresponding to the date and time set by the user */
                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

                    /** Converting the date and time in to milliseconds elapsed since epoch */
                    long alarm_time = calendar.getTimeInMillis();

                    if (calendar.before(Calendar.getInstance()))
                        alarm_time += AlarmManager.INTERVAL_DAY * 7;

                    assert alarmManager != null;
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarm_time,
                            AlarmManager.INTERVAL_DAY * 7, operation);
                }
            }
            ToastMessage message = new ToastMessage(getActivity());
            message.messageError(getResources().getString(R.string.alarm_for) + " " + pill_name + " " + getResources().getString(R.string.alarm_for2), false);
            showMedicineList();
        }
    };
}
