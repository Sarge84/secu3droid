package org.secu3.android.fragments;

import org.secu3.android.R;
import org.secu3.android.api.io.Secu3Dat;
import org.secu3.android.api.io.Secu3Dat.TemperPar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

public class TemperFragment extends Fragment implements ISecu3Fragment{
	TemperPar packet;
	
	EditText fanOn;
	EditText fanOff;
	CheckBox useTempSensor;
	CheckBox usePWM;
	CheckBox useTable;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) return null;
		
		return inflater.inflate(R.layout.temper_params, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		fanOn = (EditText)getView().findViewById(R.id.temperFanOnEditText);
		fanOff = (EditText)getView().findViewById(R.id.temperFanOffEditText);
		useTempSensor = (CheckBox)getView().findViewById(R.id.temperUseTempSensorCheckBox);
		usePWM = (CheckBox)getView().findViewById(R.id.temperUsePWMCheckBox);
		useTable = (CheckBox)getView().findViewById(R.id.temperUseTableCheckBox);		
	}
	
	@Override
	public void onResume() {
		updateData();		
		super.onResume();
	}

	@Override
	public void updateData() {
		if (packet != null) {		
			fanOn.setText(String.valueOf(((TemperPar)packet).vent_on));
			fanOff.setText(String.valueOf(((TemperPar)packet).vent_off));
			useTempSensor.setChecked(((TemperPar)packet).tmp_use != 0);
			usePWM.setChecked(((TemperPar)packet).vent_pwm != 0);
			useTable.setChecked(((TemperPar)packet).cts_use_map != 0);
		}
	}

	@Override
	public void setData(Secu3Dat packet) {
		this.packet = (TemperPar) packet;		
	}

	@Override
	public Secu3Dat getData() {
		packet.vent_on = Float.valueOf(fanOn.getText().toString());
		packet.vent_off = Float.valueOf(fanOff.getText().toString());
		packet.tmp_use = useTempSensor.isChecked()?1:0;
		packet.vent_pwm = usePWM.isChecked()?1:0;
		packet.cts_use_map = useTable.isChecked()?1:0;
		return packet;
	}
}
