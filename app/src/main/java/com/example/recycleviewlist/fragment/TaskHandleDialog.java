package com.example.recycleviewlist.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.OnlineUser;
import com.example.recycleviewlist.R;
import com.example.recycleviewlist.database.task.TaskRepository;
import com.example.recycleviewlist.model.State;
import com.example.recycleviewlist.model.StateHandler;
import com.example.recycleviewlist.model.Task;
import com.example.recycleviewlist.utils.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class TaskHandleDialog extends DialogFragment {
    public static final String KEY_STATE_HANDLER = "com.example.recycleviewlist.activitystateForAdd";
    public static final String KEY_UUID_TASK = "com.example.recycleviewlist.actitaskKey";
    public static final String KEY_UUID = "com.example.recycleviewlist.fragmentRESULTUUUID";
    public static final String KEY_STATE = "com.example.recycleviewlist.fragmentSTATE";
    public static final int REQUEST_CODE_IMAGE_CAMERA = 0;
    public static final int RESULT_LOAD_IMG = 1;
    private EditText mEditTextName;
    private EditText mEditTextDiscreption;
    private UUID mTaskUUID;
    private Task mTask;
    private TextView mTextViewTimeShow;
    private State mState = State.DONE;
    private StateHandler mStateHandler;
    private RadioGroup mRadioGroupStete;
    private RadioButton mRadioButtonDone;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonTodo;

    private ImageButton mImageButtonShare;
    private ImageButton mImageButtonFlder;
    private ImageButton mImageButtonCamera;

    private ImageView mImageViewTaskDetail;
    private File mImagePath;

    public static TaskHandleDialog newInstance(StateHandler state, UUID taskUUID) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_STATE_HANDLER, state);
        args.putSerializable(KEY_UUID_TASK, taskUUID);
        TaskHandleDialog fragment = new TaskHandleDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.handler_dialog_fragment, null);
        findView(view);
        setOnClick();
        setArguments();
        initViews();
        return new AlertDialog.Builder(getActivity())
                .setTitle(mStateHandler == StateHandler.NEW ?
                        "new Task " + mState.toString() :
                        "editing task")
                .setIcon(R.drawable.ic_task_handeler)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        okClickOk();
                        setResult();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setNeutralButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        deleteTask();
                        setResult();
                    }
                })
                .create();
    }

    private void setOnClick() {

        mImageButtonShare.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getTextShare());
                        startActivity(shareIntent);
                    }

                    private String getTextShare() {
                        return "This is task manager app" +
                                "\nUSER : " + OnlineUser.newInstance().getOnlineUser().getStringName() +
                                "\nTASK : " + mTask.getStringTitle() +
                                "\nDESCRIPTION : " + mTask.getStringDescription() +
                                "\nTIME : " + mTask.getDate().toString() +
                                "\nSTATE : " + (mTask.getState().toString()).toLowerCase();
                    }
                }
        );
        mImageButtonFlder.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                    }
                }
        );
        mImageButtonCamera.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePicture.resolveActivity(getActivity().getPackageManager()) != null)
                            startActivityForResult(takePicture, REQUEST_CODE_IMAGE_CAMERA);
                    }
                }
        );
    }

    private void setArguments() {
        mTaskUUID = (UUID) getArguments().getSerializable(KEY_UUID_TASK);
        mTask = TaskRepository.getInstance(getActivity()).getTask(mTaskUUID);
        mState = mTask.getState();
        mStateHandler = (StateHandler) getArguments().getSerializable(KEY_STATE_HANDLER);
        mImagePath = Image.getTaskImagePath(mTask, getContext());
    }

    private void deleteTask() {
        TaskRepository.getInstance(getActivity()).removeTask(mTask);
    }

    private void initViews() {
        setStateRadioGroup(mTask.getState());
        mTextViewTimeShow.setText(mTask.getDate().toString());
        mEditTextDiscreption.setText(mTask.getStringDescription());
        mEditTextName.setText(mTask.getStringTitle());
        mImageViewTaskDetail.setImageBitmap(
        Image.loadBitMap(mTask, getContext()
        ));
        checkInvisibleButtonImage();
    }

    private void checkInvisibleButtonImage() {
        if (mTask.getHasImage()) {

            mImageButtonCamera.setVisibility(View.INVISIBLE);
            mImageButtonFlder.setVisibility(View.INVISIBLE);
        }
    }


    private void okClickOk() {
        mTask.setStringTitle(mEditTextName.getText().toString());
        mTask.setStringDescription(mEditTextDiscreption.getText().toString());
        mTask.setState(getSatateRadioGroup());
/*        if (mStateHandler == StateHandler.NEW) {
            TaskRepository.getInstance(getActivity()).addTask(mTask);
        } else if (mStateHandler == StateHandler.EDIT) {*/
        TaskRepository.getInstance(getActivity()).setTask(mTask);
        //  }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.handler_dialog_fragment, container, false);
        return view;
    }

    private void findView(View view) {
        mEditTextName = view.findViewById(R.id.editText_name);
        mTextViewTimeShow = view.findViewById(R.id.textView_time);
        mRadioGroupStete = view.findViewById(R.id.RadioGroup_state);
        mEditTextDiscreption = view.findViewById(R.id.editText_discription);
        mRadioButtonDone = view.findViewById(R.id.radioButton_done);
        mRadioButtonDoing = view.findViewById(R.id.radioButton_doing);
        mRadioButtonTodo = view.findViewById(R.id.radioButton_todo);
        mImageButtonShare = view.findViewById(R.id.button_share);
        mImageButtonFlder = view.findViewById(R.id.button_folder);
        mImageButtonCamera = view.findViewById(R.id.button_camera);
        mImageViewTaskDetail = view.findViewById(R.id.imageView_detail_handeler);

    }


    private State getSatateRadioGroup() {
        switch (mRadioGroupStete.getCheckedRadioButtonId()) {
            case R.id.radioButton_done: {
                return State.DONE;
            }
            case R.id.radioButton_doing: {
                return State.DOING;
            }
            case R.id.radioButton_todo: {
                return State.TODO;
            }
        }
        return null;

    }

    private void setStateRadioGroup(State state) {

        switch (state) {
            case DONE: {
                mRadioButtonDone.setChecked(true);
                break;
            }
            case TODO: {
                mRadioButtonTodo.setChecked(true);
                break;
            }
            case DOING: {
                mRadioButtonDoing.setChecked(true);
                break;
            }
        }
    }

    private void setResult() {
        Fragment fragment = getTargetFragment();
        Intent data = new Intent();
        data.putExtra(KEY_STATE, mTask.getState());
        data.putExtra(KEY_UUID, mTaskUUID);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        } else if (requestCode == REQUEST_CODE_IMAGE_CAMERA) {
            Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
            Image.saveImageTask(mTask, selectedImage, getContext());
            mImageViewTaskDetail.setImageBitmap(selectedImage);

        } else if (requestCode == RESULT_LOAD_IMG) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Image.saveImageTask(mTask, selectedImage, getContext());
                mImageViewTaskDetail.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        checkInvisibleButtonImage();
    }
}
