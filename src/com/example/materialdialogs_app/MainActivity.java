package com.example.materialdialogs_app;

import java.io.File;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.MaterialDialog.InputCallback;
import com.afollestad.materialdialogs.MaterialDialog.ListCallback;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackMultiChoice;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackSingleChoice;
import com.afollestad.materialdialogs.MaterialDialog.ListLongCallback;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.afollestad.materialdialogs.color.CircleView;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.folderselector.FileChooserDialog;
import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter.Callback;
import com.afollestad.materialdialogs.util.DialogUtils;

import com.example.materialdialogs_app.ButtonItemAdapter.ButtonCallback;
import com.example.materialdialogs_app.ButtonItemAdapter.ItemCallback;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import me.zhanghai.android.materialprogressbar.IndeterminateCircularProgressDrawable;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends AppCompatActivity implements OnClickListener,ColorChooserDialog.ColorCallback,FolderChooserDialog.FolderCallback,
FileChooserDialog.FileCallback {
	private Button btnBasicNoTitle, btnBasic, btnbasicLongContent, btnbasicIcon, btnbasicCheckPrompt;
	private Toast toast;
	private Button btnStacked, btnNeutral, btnCallbacks;
	private Button btnListNoTitle, btnList, btnLongList, btnLongItemsList, btnListCheckPromt, btnListLongPress;
	private int primaryPreselect;
	private int accentPreselect;
	private Button btnSingleChoice, btnSingleChoice_longItems, btnMultiChoice, btnMultiChoiceLimited;
	private Button btnMultiChoiceLimitedMin, btnMultiChoice_longItems, btnMultiChoice_disabledItems;
	private Button btnSimpleList,btnCustomListItems,btnCustomView,btnCustomView_webView;
	
	// Custom View Dialog
	private EditText passwordInput;
	private View positiveAction;
	private Button btnColorChooser,btnColorChooser_accent,btnColorChooser_customColors,btnColorChooser_customColorsNosub;
	private Button btnInput,btnInput_custominvalidation,btnInput_checkPrompt;
	private Button btnProgress1,btnProgress2,btnProgress3;
	static int index = 0;
	private Thread thread;
	private Button btnPreference,btnThemed,btnShowCancelDismiss,btnFile_chooser,btnFolder_chooser;
	private int chooserDialog;
	private Handler handler;
	private static final int STORAGE_PERMISSION_RC = 69;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		primaryPreselect = DialogUtils.resolveColor(this, R.attr.colorPrimary);
		accentPreselect = DialogUtils.resolveColor(this, R.attr.colorAccent);
		initBasicView();
		initActionButton();
		initList();
		initChoiceLists();
		initAdvancedLists();
		initCustomView();
		initColorChooser();
		initInputView();		
		initProgress();
		initOther();	
		handler = new Handler();
		primaryPreselect = DialogUtils.resolveColor(this, R.attr.colorPrimary);
		accentPreselect = DialogUtils.resolveColor(this, R.attr.colorAccent);
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler = null;
	}
	private void initOther() {
		btnPreference = (Button) findViewById(R.id.preference_dialogs);
		btnPreference.setOnClickListener(this);	
		
		btnThemed = (Button) findViewById(R.id.themed);
		btnThemed.setOnClickListener(this);			
		btnShowCancelDismiss = (Button) findViewById(R.id.showCancelDismiss);
		btnShowCancelDismiss.setOnClickListener(this);	
		btnFile_chooser = (Button) findViewById(R.id.file_chooser);
		btnFile_chooser.setOnClickListener(this);	
		btnFolder_chooser = (Button) findViewById(R.id.folder_chooser);
		btnFolder_chooser.setOnClickListener(this);	
		
	}
	private void startThread(Runnable run) {
		if (thread != null) {
			thread.interrupt();
		}
		thread = new Thread(run);
		thread.start();
	}
	@Override
	protected void onPause() {
		super.onPause();
		if (thread != null && !thread.isInterrupted() && thread.isAlive()) {
			thread.interrupt();
		}
	}
	private void initProgress() {
		btnProgress1 = (Button) findViewById(R.id.progress1);
		btnProgress1.setOnClickListener(this);	
		btnProgress2 = (Button) findViewById(R.id.progress2);
		btnProgress2.setOnClickListener(this);	
		btnProgress3 = (Button) findViewById(R.id.progress3);
		btnProgress3.setOnClickListener(this);	
		
	}

	private void initInputView() {
		btnInput = (Button) findViewById(R.id.input);
		btnInput.setOnClickListener(this);	
		
		btnInput_custominvalidation = (Button) findViewById(R.id.input_custominvalidation);
		btnInput_custominvalidation.setOnClickListener(this);
		
		btnInput_checkPrompt = (Button) findViewById(R.id.input_checkPrompt);
		btnInput_checkPrompt.setOnClickListener(this);			
		
	}

	private void initColorChooser() {
		btnColorChooser = (Button) findViewById(R.id.colorChooser_primary);
		btnColorChooser.setOnClickListener(this);
		
		btnColorChooser_accent = (Button) findViewById(R.id.colorChooser_accent);
		btnColorChooser_accent.setOnClickListener(this);
		
		btnColorChooser_customColors = (Button) findViewById(R.id.colorChooser_customColors);
		btnColorChooser_customColors.setOnClickListener(this);
		
		btnColorChooser_customColorsNosub = (Button) findViewById(R.id.colorChooser_customColorsNoSub);
		btnColorChooser_customColorsNosub.setOnClickListener(this);
		
	}

	private void initCustomView() {
		btnCustomView = (Button) findViewById(R.id.customView);
		btnCustomView.setOnClickListener(this);

		btnCustomView_webView = (Button) findViewById(R.id.customView_webView);
		btnCustomView_webView.setOnClickListener(this);	
		
	}

	private void initAdvancedLists() {
		btnSimpleList = (Button) findViewById(R.id.simpleList);
		btnSimpleList.setOnClickListener(this);

		btnCustomListItems = (Button) findViewById(R.id.customListItems);
		btnCustomListItems.setOnClickListener(this);		
	}

	private void initChoiceLists() {
		btnSingleChoice = (Button) findViewById(R.id.singleChoice);
		btnSingleChoice.setOnClickListener(this);

		btnSingleChoice_longItems = (Button) findViewById(R.id.singleChoice_longItems);
		btnSingleChoice_longItems.setOnClickListener(this);

		btnMultiChoice = (Button) findViewById(R.id.multiChoice);
		btnMultiChoice.setOnClickListener(this);
		btnMultiChoiceLimited = (Button) findViewById(R.id.multiChoiceLimited);
		btnMultiChoiceLimited.setOnClickListener(this);
		btnMultiChoiceLimitedMin = (Button) findViewById(R.id.multiChoiceLimitedMin);
		btnMultiChoiceLimitedMin.setOnClickListener(this);
		btnMultiChoice_longItems = (Button) findViewById(R.id.multiChoice_longItems);
		btnMultiChoice_longItems.setOnClickListener(this);

		btnMultiChoice_disabledItems = (Button) findViewById(R.id.multiChoice_disabledItems);
		btnMultiChoice_disabledItems.setOnClickListener(this);

	}

	private void initList() {
		btnListNoTitle = (Button) findViewById(R.id.listNoTitle);
		btnListNoTitle.setOnClickListener(this);

		btnList = (Button) findViewById(R.id.list);
		btnList.setOnClickListener(this);

		btnLongList = (Button) findViewById(R.id.longList);
		btnLongList.setOnClickListener(this);

		btnLongItemsList = (Button) findViewById(R.id.list_longItems);
		btnLongItemsList.setOnClickListener(this);

		btnListCheckPromt = (Button) findViewById(R.id.list_checkPrompt);
		btnListCheckPromt.setOnClickListener(this);

		btnListLongPress = (Button) findViewById(R.id.list_longPress);
		btnListLongPress.setOnClickListener(this);
	}

	private void initActionButton() {
		btnStacked = (Button) findViewById(R.id.stacked);
		btnStacked.setOnClickListener(this);

		btnNeutral = (Button) findViewById(R.id.neutral);
		btnNeutral.setOnClickListener(this);

		btnCallbacks = (Button) findViewById(R.id.callbacks);
		btnCallbacks.setOnClickListener(this);
	}

	private void initBasicView() {
		btnBasicNoTitle = (Button) findViewById(R.id.basicNoTitle);
		btnBasicNoTitle.setOnClickListener(this);

		btnBasic = (Button) findViewById(R.id.basic);
		btnBasic.setOnClickListener(this);

		btnbasicLongContent = (Button) findViewById(R.id.basicLongContent);
		btnbasicLongContent.setOnClickListener(this);

		btnbasicIcon = (Button) findViewById(R.id.basicIcon);
		btnbasicIcon.setOnClickListener(this);

		btnbasicCheckPrompt = (Button) findViewById(R.id.basicCheckPrompt);
		btnbasicCheckPrompt.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.about) {
			AboutDialog.show(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showToast(String message) {
		if (toast != null) {
			toast.cancel();
			toast = null;
		}
		toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		toast.show();
	}

	private void showToast(@StringRes int message) {
		showToast(getString(message));
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.basicNoTitle:
			new MaterialDialog.Builder(this).content(R.string.shareLocationPrompt).positiveText(R.string.agree)
					.negativeText(R.string.disagree).show();
			break;
		case R.id.basic:
			new MaterialDialog.Builder(this).title(R.string.useGoogleLocationServices)
					.content(R.string.useGoogleLocationServicesPrompt, true).positiveText(R.string.agree)
					.negativeText(R.string.disagree).show();
			break;
		case R.id.basicLongContent:
			new MaterialDialog.Builder(this).title(R.string.useGoogleLocationServices).content(R.string.loremIpsum)
					.positiveText(R.string.agree).negativeText(R.string.disagree)
					.checkBoxPrompt("Hello, world!", true, null).show();
			break;
		case R.id.basicIcon:
			new MaterialDialog.Builder(this).iconRes(R.mipmap.ic_launcher).limitIconToDefaultSize() // limits
					// the displayed icon size to 48dp
					.title(R.string.useGoogleLocationServices).content(R.string.useGoogleLocationServicesPrompt, true)
					.positiveText(R.string.agree).negativeText(R.string.disagree).onNegative(new SingleButtonCallback() {
						
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							showToast("取消！");
							
						}
					}).onPositive(new SingleButtonCallback() {
						
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							showToast("同意！");
							
						}
					}).show();
			break;
		case R.id.basicCheckPrompt:
			new MaterialDialog.Builder(this).iconRes(R.mipmap.ic_launcher).limitIconToDefaultSize()
					.title(Html.fromHtml(getString(R.string.permissionSample, getString(R.string.app_name))))
					.positiveText(R.string.allow).negativeText(R.string.deny).onAny(new SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							showToast("Prompt checked? " + dialog.isPromptCheckBoxChecked());
						}
					}).checkBoxPromptRes(R.string.dont_ask_again, false, null).show();
			break;

		case R.id.stacked:
			new MaterialDialog.Builder(this).title(R.string.useGoogleLocationServices)
					.content(R.string.useGoogleLocationServicesPrompt, true).positiveText(R.string.speedBoost)
					.negativeText(R.string.noThanks).btnStackedGravity(GravityEnum.END)
					.stackingBehavior(StackingBehavior.ALWAYS).show();
			// this generally should not be forced, but is used for demo purposes
			break;
		case R.id.neutral:
			new MaterialDialog.Builder(this).title(R.string.useGoogleLocationServices)
					.content(R.string.useGoogleLocationServicesPrompt, true).positiveText(R.string.agree)
					.negativeText(R.string.disagree).neutralText(R.string.more_info).show();
			break;
		case R.id.callbacks:
			new MaterialDialog.Builder(this).title(R.string.useGoogleLocationServices)
					.content(R.string.useGoogleLocationServicesPrompt, true).positiveText(R.string.agree)
					.negativeText(R.string.disagree).neutralText(R.string.more_info).onAny(new SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							//showToast(which.name() + "!");
							if( which.equals(DialogAction.POSITIVE)){
								showToast(which.name() + "退出!");
								dialog.dismiss();
								System.exit(0);
							}else if(which.equals(DialogAction.NEGATIVE)){
								showToast(which.name() + "取消!");
								dialog.dismiss();
							}
						}
					}).show();
			break;

		case R.id.listNoTitle:
			new MaterialDialog.Builder(this).items(R.array.socialNetworks).itemsCallback(new ListCallback() {
				@Override
				public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
					showToast(which + ": " + text);
				}
			}).show();
			/*
			 * new MaterialDialog.Builder(this) .title("List Dialog")
			 * .iconRes(R.drawable.ic_launcher)
			 * .content("List Dialog,显示数组信息，高度会随着内容扩大")
			 * .items(R.array.socialNetworks)
			 * .listSelector(R.color.green)//列表的背景颜色 .autoDismiss(false)//不自动消失
			 * .show();
			 */
			break;
		case R.id.list:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks)
					.itemsCallback(new ListCallback() {
						@Override
						public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
							showToast(which + ": " + text);
						}
					}).show();
			break;
		case R.id.longList:
			new MaterialDialog.Builder(this).title(R.string.states).items(R.array.states)
					.itemsCallback(new ListCallback() {
						@Override
						public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
							showToast(which + ": " + text);
						}
					}).positiveText(android.R.string.cancel).show();
			break;
		case R.id.list_longItems:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks_longItems)
					.itemsCallback(new ListCallback() {
						@Override
						public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
							showToast(which + ": " + text);
						}
					}).show();
			break;
		case R.id.list_checkPrompt:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks)
					.itemsCallback(new ListCallback() {
						@Override
						public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
							showToast(which + ": " + text);
						}
					}).checkBoxPromptRes(R.string.example_prompt, true, null).negativeText(android.R.string.cancel)
					.show();
			break;
		case R.id.list_longPress:
			index = 0;
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks)
					.itemsCallback(new ListCallback() {
						@Override
						public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
							showToast(which + ": " + text);
						}
					}).autoDismiss(false).itemsLongCallback(new ListLongCallback() {
						@Override
						public boolean onLongSelection(MaterialDialog dialog, View itemView, int position,
								CharSequence text) {
							dialog.getItems().remove(position);
							dialog.notifyItemsChanged();
							return false;
						}
					}).onNeutral(new SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							index++;
							dialog.getItems().add("Item " + index);
							dialog.notifyItemInserted(dialog.getItems().size() - 1);
						}
					}).neutralText(R.string.add_item).show();
			break;
		case R.id.singleChoice:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks)
					.itemsDisabledIndices(1, 3).itemsCallbackSingleChoice(2, new ListCallbackSingleChoice() {
						@Override
						public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
							showToast(which + ": " + text);
							return true; // allow selection
						}
					}).positiveText(R.string.md_choose_label).show();
			break;
		case R.id.singleChoice_longItems:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks_longItems)
					.itemsCallbackSingleChoice(2, new ListCallbackSingleChoice() {
						@Override
						public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
							showToast(which + ": " + text);
							return true; // allow selection
						}
					}).positiveText(R.string.md_choose_label).show();
			break;
		case R.id.multiChoice:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks)
					.itemsCallbackMultiChoice(new Integer[] { 1, 3 }, new ListCallbackMultiChoice() {
						@Override
						public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
							StringBuilder str = new StringBuilder();
							for (int i = 0; i < which.length; i++) {
								if (i > 0) {
									str.append('\n');
								}
								str.append(which[i]);
								str.append(": ");
								str.append(text[i]);
							}
							showToast(str.toString());
							return true; // allow selection
						}
					}).onNeutral(new SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							dialog.clearSelectedIndices();
						}
					}).onPositive(new SingleButtonCallback() {
						@Override
						public void onClick(MaterialDialog dialog, DialogAction which) {
							dialog.dismiss();
						}
					}).alwaysCallMultiChoiceCallback().positiveText(R.string.md_choose_label).autoDismiss(false)
					.neutralText(R.string.clear_selection).show();
			break;
		case R.id.multiChoiceLimited:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks)
					.itemsCallbackMultiChoice(new Integer[] { 1 }, new ListCallbackMultiChoice() {
						@Override
						public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
							boolean allowSelectionChange = which.length <= 2;
							// limit selection to 2,the new (un)selection is
							// included in the which array
							if (!allowSelectionChange) {
								showToast(R.string.selection_limit_reached);
							}
							return allowSelectionChange;
						}
					}).positiveText(R.string.dismiss).alwaysCallMultiChoiceCallback().show();
			// the callback will always be called, to check if (un)selection is
			// still allowed
			break;
		case R.id.multiChoiceLimitedMin:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks)
			.itemsCallbackMultiChoice(new Integer[] { 1 }, new ListCallbackMultiChoice() {
				@Override
				public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
					boolean allowSelectionChange = which.length >= 1; 
					// selection count must	 stay above 1,the new (un)selection is included in the which array
					if (!allowSelectionChange) {
						showToast(R.string.selection_min_limit_reached);
					}
					return allowSelectionChange;
				}
			}).positiveText(R.string.dismiss).alwaysCallMultiChoiceCallback() 
			.show();
			// the callback	 will always be	 called, to	 check if (un)selection	 is	 still allowed
			break;
		case R.id.multiChoice_longItems:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks_longItems)
			.itemsCallbackMultiChoice(new Integer[] { 1, 3 }, new ListCallbackMultiChoice() {
				@Override
				public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
					StringBuilder str = new StringBuilder();
					for (int i = 0; i < which.length; i++) {
						if (i > 0) {
							str.append('\n');
						}
						str.append(which[i]);
						str.append(": ");
						str.append(text[i]);
					}
					showToast(str.toString());
					return true; // allow selection
				}
			}).positiveText(R.string.md_choose_label).show();
			break;
		case R.id.multiChoice_disabledItems:
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).items(R.array.socialNetworks)
			.itemsCallbackMultiChoice(new Integer[] { 0, 1, 2 }, new ListCallbackMultiChoice() {
				@Override
				public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
					StringBuilder str = new StringBuilder();
					for (int i = 0; i < which.length; i++) {
						if (i > 0) {
							str.append('\n');
						}
						str.append(which[i]);
						str.append(": ");
						str.append(text[i]);
					}
					showToast(str.toString());
					return true; // allow selection
				}
			}).onNeutral(new SingleButtonCallback() {
				@Override
				public void onClick(MaterialDialog dialog, DialogAction which) {
					dialog.clearSelectedIndices();
				}
			}).alwaysCallMultiChoiceCallback()
			.positiveText(R.string.md_choose_label).autoDismiss(false).neutralText(R.string.clear_selection)
			.itemsDisabledIndices(0, 1).show();
			break;
		case R.id.simpleList:
			final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(
					new Callback() {
						@Override
						public void onMaterialListItemSelected(MaterialDialog dialog, int index1,
								MaterialSimpleListItem item) {
							showToast(item.getContent().toString());
						}
					});
			adapter.add(new MaterialSimpleListItem.Builder(this).content("username@gmail.com")
					.icon(R.drawable.ic_account_circle).backgroundColor(Color.WHITE).build());
			adapter.add(new MaterialSimpleListItem.Builder(this).content("user02@gmail.com")
					.icon(R.drawable.ic_account_circle).backgroundColor(Color.WHITE).build());
			adapter.add(new MaterialSimpleListItem.Builder(this).content(R.string.add_account)
					.icon(R.drawable.ic_content_add).iconPaddingDp(8).build());

			new MaterialDialog.Builder(this).title(R.string.set_backup).adapter(adapter, null).show();
			break;
		case R.id.customListItems:
			final ButtonItemAdapter adapter1 = new ButtonItemAdapter(this, R.array.socialNetworks);
			adapter1.setCallbacks(new ItemCallback() {
				@Override
				public void onItemClicked(int itemIndex) {
					showToast("Item clicked: " + itemIndex);
				}
			},
					new ButtonCallback() {
						@Override
						public void onButtonClicked(int buttonIndex) {
							showToast("Button clicked: " + buttonIndex);
						}
					});
			new MaterialDialog.Builder(this).title(R.string.socialNetworks).adapter(adapter1, null).show();
			break;
		case R.id.customView:
			showCustomView();
			break;
		case R.id.customView_webView:
			int accentColor = ThemeSingleton.get().widgetColor;
			if (accentColor == 0) {
				accentColor = ContextCompat.getColor(this, R.color.accent);
			}
			ChangelogDialog.create(false, accentColor).show(getSupportFragmentManager(), "changelog");
			break;
		case R.id.colorChooser_primary:
			new ColorChooserDialog.Builder(this, R.string.color_palette).titleSub(R.string.colors)
			.preselect(primaryPreselect).show(this);
			break;
		case R.id.colorChooser_accent:
			new ColorChooserDialog.Builder(this, R.string.color_palette).titleSub(R.string.colors).accentMode(true)
			.preselect(accentPreselect).show(this);
			break;
		case R.id.colorChooser_customColors:
			int[][] subColors = new int[][] {
				new int[] { Color.parseColor("#EF5350"), Color.parseColor("#F44336"), Color.parseColor("#E53935") },
				new int[] { Color.parseColor("#EC407A"), Color.parseColor("#E91E63"), Color.parseColor("#D81B60") },
				new int[] { Color.parseColor("#AB47BC"), Color.parseColor("#9C27B0"), Color.parseColor("#8E24AA") },
				new int[] { Color.parseColor("#7E57C2"), Color.parseColor("#673AB7"), Color.parseColor("#5E35B1") },
				new int[] { Color.parseColor("#5C6BC0"), Color.parseColor("#3F51B5"), Color.parseColor("#3949AB") },
				new int[] { Color.parseColor("#42A5F5"), Color.parseColor("#2196F3"), Color.parseColor("#1E88E5") } };

		new ColorChooserDialog.Builder(this, R.string.color_palette).titleSub(R.string.colors)
				.preselect(primaryPreselect).customColors(R.array.custom_colors, subColors).show(this);
			break;
		case R.id.colorChooser_customColorsNoSub:
			new ColorChooserDialog.Builder(this, R.string.color_palette).titleSub(R.string.colors)
			.preselect(primaryPreselect).customColors(R.array.custom_colors, null).show(this);
			break;
		case R.id.input:
			new MaterialDialog.Builder(this).title(R.string.input).content(R.string.input_content)
			.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
					| InputType.TYPE_TEXT_FLAG_CAP_WORDS)
			.inputRange(2, 16).positiveText(R.string.submit).input(R.string.input_hint, R.string.input_hint, false,
					new InputCallback() {
						@Override
						public void onInput(MaterialDialog dialog, CharSequence input) {
							showToast("Hello, " + input.toString() + "!");
						}
					})
			.show();
			break;
		case R.id.input_custominvalidation:
			new MaterialDialog.Builder(this).title(R.string.input).content(R.string.input_content_custominvalidation)
			.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
					| InputType.TYPE_TEXT_FLAG_CAP_WORDS)
			.positiveText(R.string.submit).alwaysCallInputCallback() 
			.input(R.string.input_hint, 0, false, new InputCallback() {
				@Override
				public void onInput(MaterialDialog dialog, CharSequence input) {
					if (input.toString().equalsIgnoreCase("hello")) {
						dialog.setContent("I told you not to type that!");
						dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
					} else {
						dialog.setContent(R.string.input_content_custominvalidation);
						dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
					}
				}
			}).show();// this forces the callback to be invoked with every input change
			break;
		case R.id.input_checkPrompt:
			new MaterialDialog.Builder(this).title(R.string.input).content(R.string.input_content)
			.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
					| InputType.TYPE_TEXT_FLAG_CAP_WORDS)
			.inputRange(2, 16).positiveText(R.string.submit)
			.input(R.string.input_hint, R.string.input_hint, false,
					new InputCallback() {
						@Override
						public void onInput(MaterialDialog dialog, CharSequence input) {
							showToast("Hello, " + input.toString() + "!");
						}
					})
			.checkBoxPromptRes(R.string.example_prompt, true, null).show();
			break;
		case R.id.progress1:
			showProgressDeterminateDialog();
			break;
		case R.id.progress2:
			showIndeterminateProgressDialog(false);
			break;
		case R.id.progress3:
			showIndeterminateProgressDialog(true);
			break;
		case R.id.themed:
			new MaterialDialog.Builder(this).title(R.string.useGoogleLocationServices)
			.content(R.string.useGoogleLocationServicesPrompt, true).positiveText(R.string.agree)
			.negativeText(R.string.disagree).positiveColorRes(R.color.material_red_400)
			.negativeColorRes(R.color.material_red_400).titleGravity(GravityEnum.CENTER)
			.titleColorRes(R.color.material_red_400).contentColorRes(android.R.color.white)
			.backgroundColorRes(R.color.material_blue_grey_800).dividerColorRes(R.color.accent)
			.btnSelector(R.drawable.md_btn_selector_custom, DialogAction.POSITIVE).positiveColor(Color.WHITE)
			.negativeColorAttr(android.R.attr.textColorSecondaryInverse).theme(Theme.DARK).show();
			break;
		case R.id.preference_dialogs:
			startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
			break;
		case R.id.showCancelDismiss:
			showShowCancelDismissCallbacks();
			break;
		case R.id.file_chooser:
			showFileChooser();
			break;
		case R.id.folder_chooser:
			showFolderChooser();
			break;
		}		
	}
	public void showFileChooser() {
		chooserDialog = R.id.file_chooser;
		if (ActivityCompat.checkSelfPermission(MainActivity.this,
				Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(MainActivity.this,
					new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, STORAGE_PERMISSION_RC);
			return;
		}
		new FileChooserDialog.Builder(this).show(this);
	}
		
	public void showShowCancelDismissCallbacks() {
		new MaterialDialog.Builder(this).title(R.string.useGoogleLocationServices)
				.content(R.string.useGoogleLocationServicesPrompt).positiveText(R.string.agree)
				.negativeText(R.string.disagree).neutralText(R.string.more_info)
				.showListener(new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						showToast("onShow");
					}
				}).cancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						showToast("onCancel");
					}
				})
				.dismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						showToast("onDismiss");
					}
				}).show();
	}
	
	public void showFolderChooser() {
		chooserDialog = R.id.folder_chooser;
		if (ActivityCompat.checkSelfPermission(MainActivity.this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(MainActivity.this,
					new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, STORAGE_PERMISSION_RC);
			return;
		}
		new FolderChooserDialog.Builder(MainActivity.this).chooseButton(R.string.md_choose_label)
				.allowNewFolder(true, 0).show(this);
	}
	
	private void showIndeterminateProgressDialog(boolean horizontal) {
		new MaterialDialog.Builder(this).title(R.string.progress_dialog).content(R.string.please_wait).progress(true, 0)
		.progressIndeterminateStyle(horizontal).show();
		
	}

	public void showProgressDeterminateDialog() {
		new MaterialDialog.Builder(this).title(R.string.progress_dialog).content(R.string.please_wait)
				.contentGravity(GravityEnum.CENTER).progress(false, 150, true).cancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (thread != null) {
							thread.interrupt();
						}
					}
				}).showListener(new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialogInterface) {
						final MaterialDialog dialog = (MaterialDialog) dialogInterface;
						startThread(new Runnable() {
							@Override
							public void run() {
								while (dialog.getCurrentProgress() != dialog.getMaxProgress()
										&& !Thread.currentThread().isInterrupted()) {
									if (dialog.isCancelled()) {
										break;
									}
									try {
										Thread.sleep(50);
									} catch (InterruptedException e) {
										break;
									}
									dialog.incrementProgress(1);
								}
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										thread = null;
										dialog.setContent(getString(R.string.md_done_label));
									}
								});
							}
						});
					}
				}).show();
	}

	public void showCustomView() {
		MaterialDialog dialog = new MaterialDialog.Builder(this).title(R.string.googleWifi)
				.customView(R.layout.dialog_customview, true).positiveText(R.string.connect)
				.negativeText(android.R.string.cancel)
				.onPositive(new SingleButtonCallback() {
					@Override
					public void onClick(MaterialDialog dialog1, DialogAction which) {
						showToast("Password: " + passwordInput.getText().toString());
					}
				}).build();

		positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
		// noinspection ConstantConditions
		passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.password);
		passwordInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				positiveAction.setEnabled(s.toString().trim().length() > 0);
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		// Toggling the show password CheckBox will mask or unmask the password
		// input EditText
		CheckBox checkbox = (CheckBox) dialog.getCustomView().findViewById(R.id.showPassword);
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				passwordInput.setInputType(!isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
				passwordInput.setTransformationMethod(!isChecked ? PasswordTransformationMethod.getInstance() : null);
			}
		});

		int widgetColor = ThemeSingleton.get().widgetColor;
		MDTintHelper.setTint(checkbox, widgetColor == 0 ? ContextCompat.getColor(this, R.color.accent) : widgetColor);

		MDTintHelper.setTint(passwordInput,
				widgetColor == 0 ? ContextCompat.getColor(this, R.color.accent) : widgetColor);

		dialog.show();
		positiveAction.setEnabled(false); // disabled by default
	}

	@SuppressLint("NewApi")
	@Override
	public void onColorSelection(ColorChooserDialog dialog, int selectedColor) {
		if (dialog.isAccentMode()) {
			accentPreselect = selectedColor;
			ThemeSingleton.get().positiveColor = DialogUtils.getActionTextStateList(this, selectedColor);
			ThemeSingleton.get().neutralColor = DialogUtils.getActionTextStateList(this, selectedColor);
			ThemeSingleton.get().negativeColor = DialogUtils.getActionTextStateList(this, selectedColor);
			ThemeSingleton.get().widgetColor = selectedColor;
		} else {
			primaryPreselect = selectedColor;
			if (getSupportActionBar() != null) {
				getSupportActionBar().setBackgroundDrawable(new ColorDrawable(selectedColor));
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				getWindow().setStatusBarColor(CircleView.shiftColorDown(selectedColor));
				getWindow().setNavigationBarColor(selectedColor);
			}
		}
		
	}

	@Override
	public void onColorChooserDismissed(ColorChooserDialog dialog) {
		showToast("Color chooser dismissed!");
		
	}
	@Override
	public void onFileSelection(FileChooserDialog dialog, File file) {
		showToast(file.getAbsolutePath());
		
	}
	@Override
	public void onFileChooserDismissed(FileChooserDialog dialog) {
		showToast("File chooser dismissed!");
		
	}
	@Override
	public void onFolderSelection(FolderChooserDialog dialog, File folder) {
		showToast(folder.getAbsolutePath());
		
	}
	@Override
	public void onFolderChooserDismissed(FolderChooserDialog dialog) {
		showToast("Folder chooser dismissed!");
		
	}

	
}
