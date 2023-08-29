package my.edu.utar.hotelbooking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String ARG_DATE_ID = "date_id";

    public static DatePickerFragment newInstance(int dateId) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DATE_ID, dateId);
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Bundle args = getArguments();
        if (args != null) {
            int dateId = args.getInt(ARG_DATE_ID);
            return new DatePickerDialog(requireActivity(), this, year, month, day);
        } else {
            return null;
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int dateId = getArguments().getInt(ARG_DATE_ID);
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(year, month, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String selectedDate = dateFormat.format(selectedCalendar.getTime());

        if (dateId == R.id.startDateBt) {
            TextView startDateTextView = requireActivity().findViewById(R.id.startdate);
            startDateTextView.setText(selectedDate);
        } else if (dateId == R.id.endDateBt) {
            TextView endDateTextView = requireActivity().findViewById(R.id.endDate);
            endDateTextView.setText(selectedDate);
        }
    }
}

