package com.example.playtabtest;

import java.util.ArrayList;

import com.example.tiantian.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.database.Cursor;
import android.location.Address;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.RawContacts;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ConstactsEditActivity extends Activity implements OnClickListener {
	private EditText et_name;
	private EditText et_company;
	private EditText et_post;
	private EditText et_mobilephone;
	private EditText et_tellphone;
	private EditText et_email;
	private EditText et_qq;
	private EditText et_address;
	private EditText et_remark;

	private ImageView name_del;
	private ImageView company_del;
	private ImageView post_del;
	private ImageView mobilephone_del;
	private ImageView tellphone_del;
	private ImageView email_del;
	private ImageView qq_del;
	private ImageView address_del;

	private Button bt_delete;
	private TextView tv_back;
	private TextView tv_choose;

	private boolean status = false;
	private String phoneNumber = null;
	private String constactName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mi_constacts_add_edit);

		status = getIntent().getBooleanExtra("status", false);
//		status = getIntent().getBooleanExtra("status", false);
		phoneNumber = getIntent().getStringExtra("number");
		constactName = getIntent().getStringExtra("name");
		System.out.println(constactName);
		System.out.println(phoneNumber
				+ ")))))))))))))))))))))))))))))))))))))))))");
		System.out.println(status);
		
		init();
	}

	/**
	 * 初始化数�?
	 */
	private void init() {
		et_name = (EditText) findViewById(R.id.et_name);
		et_company = (EditText) findViewById(R.id.et_company);
		et_post = (EditText) findViewById(R.id.et_post);
		et_mobilephone = (EditText) findViewById(R.id.et_mobialphone);
		et_tellphone = (EditText) findViewById(R.id.et_tellphone);
		et_email = (EditText) findViewById(R.id.et_email);
		et_qq = (EditText) findViewById(R.id.et_qq);
		et_address = (EditText) findViewById(R.id.et_address);
		et_remark = (EditText) findViewById(R.id.et_remark);

		et_name.setOnClickListener(this);
		et_company.setOnClickListener(this);
		et_post.setOnClickListener(this);
		et_mobilephone.setOnClickListener(this);
		et_tellphone.setOnClickListener(this);
		et_email.setOnClickListener(this);
		et_qq.setOnClickListener(this);
		et_address.setOnClickListener(this);

		name_del = (ImageView) findViewById(R.id.name_del);
		company_del = (ImageView) findViewById(R.id.company_del);
		post_del = (ImageView) findViewById(R.id.post_del);
		mobilephone_del = (ImageView) findViewById(R.id.mobialphone_del);
		tellphone_del = (ImageView) findViewById(R.id.tellphone_del);
		email_del = (ImageView) findViewById(R.id.email_del);
		qq_del = (ImageView) findViewById(R.id.qq_del);
		address_del = (ImageView) findViewById(R.id.address_del);

		bt_delete = (Button) findViewById(R.id.bt_delete);
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_choose = (TextView) findViewById(R.id.tv_choose);

		name_del.setOnClickListener(this);
		company_del.setOnClickListener(this);
		post_del.setOnClickListener(this);
		mobilephone_del.setOnClickListener(this);
		tellphone_del.setOnClickListener(this);
		email_del.setOnClickListener(this);
		qq_del.setOnClickListener(this);
		address_del.setOnClickListener(this);

		bt_delete.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		tv_choose.setOnClickListener(this);

		if (status) {// 已存在的联系�?
			editConstacts();
		} else {// 新增联系�?
			bt_delete.setEnabled(false);
			et_mobilephone.setText(phoneNumber);
		}

	}

	/**
	 * 自动填充联系�?
	 */
	private void editConstacts() {
		// 姓名来查找个人信�?�?��联系人可能有多个号码
		if (constactName != null) {
			// 获得�?��的联系人
			Cursor cur = getContentResolver().query(
					ContactsContract.Contacts.CONTENT_URI,
					null,
					ContactsContract.Contacts.DISPLAY_NAME + "=?",
					new String[] { constactName },
					ContactsContract.Contacts.DISPLAY_NAME
							+ " COLLATE LOCALIZED ASC");
			// 循环遍历
			if (cur.moveToFirst()) {
				// 获得联系人的ID�?
				String contactId = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				// 获得联系人姓�?
				String disPlayName = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				et_name.setText(constactName);
				// 查看该联系人有多少个电话号码。如果没有这返回值为0 支持两个号码
				int phoneCount = cur
						.getInt(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (phoneCount > 0) {
					// 获得联系人的电话号码
					Cursor phones = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactId, null, null);
					if (phones.moveToFirst()) {
						int count = 0;
						do {
							// 遍历�?��的电话号�?
							String phoneNumber = phones
									.getString(phones
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							String phoneType = phones
									.getString(phones
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
							if (count == 0) {
								et_mobilephone.setText(phoneNumber);
							} else if (count == 1) {
								et_tellphone.setText(phoneNumber);
							}
							count++;

						} while (phones.moveToNext());
					}
				}

				// 获取该联系人邮箱
				Cursor emails = getContentResolver().query(
						ContactsContract.CommonDataKinds.Email.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contactId, null, null);
				if (emails.moveToFirst()) {
					// 遍历�?��的邮箱号�?
					String emailType = emails
							.getString(emails
									.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
					String emailValue = emails
							.getString(emails
									.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					et_email.setText(emailValue);
				}

				// 获取该联系人IM(QQ之类的及时�?�?
				Cursor IMs = getContentResolver().query(
						Data.CONTENT_URI,
						new String[] { Data._ID, Im.PROTOCOL, Im.DATA },
						Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
								+ Im.CONTENT_ITEM_TYPE + "'",
						new String[] { contactId }, null);
				if (IMs.moveToFirst()) {
					String protocol = IMs.getString(IMs
							.getColumnIndex(Im.PROTOCOL));
					String date = IMs.getString(IMs.getColumnIndex(Im.DATA));
					et_qq.setText(date);
				}

				// 获取该联系人地址
				Cursor address = getContentResolver()
						.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
								null,
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID
										+ " = " + contactId, null, null);
				if (address.moveToFirst()) {
					// 遍历�?��的地�?
					String street = address
							.getString(address
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
					String city = address
							.getString(address
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
					String region = address
							.getString(address
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
					String postCode = address
							.getString(address
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
					String formatAddress = address
							.getString(address
									.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
					et_address.setText(formatAddress);
				}

				// 获取该联系人组织
				Cursor organizations = getContentResolver().query(
						Data.CONTENT_URI,
						new String[] { Data._ID, Organization.COMPANY,
								Organization.TITLE },
						Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
								+ Organization.CONTENT_ITEM_TYPE + "'",
						new String[] { contactId }, null);
				if (organizations.moveToFirst()) {
					String company = organizations.getString(organizations
							.getColumnIndex(Organization.COMPANY));
					String title = organizations.getString(organizations
							.getColumnIndex(Organization.TITLE));
					et_company.setText(company);
					et_post.setText(title);
				}

				// 获取备注信息
				Cursor notes = getContentResolver().query(
						Data.CONTENT_URI,
						new String[] { Data._ID, Note.NOTE },
						Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
								+ Note.CONTENT_ITEM_TYPE + "'",
						new String[] { contactId }, null);
				if (notes.moveToFirst()) {
					String noteinfo = notes.getString(notes
							.getColumnIndex(Note.NOTE));
					et_remark.setText(noteinfo);
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.name_del:
			et_name.getText().clear();
			name_del.setVisibility(View.GONE);
			break;
		case R.id.company_del:
			et_company.getText().clear();
			company_del.setVisibility(View.GONE);
			break;
		case R.id.post_del:
			et_post.getText().clear();
			post_del.setVisibility(View.GONE);
			break;
		case R.id.mobialphone_del:
			et_mobilephone.getText().clear();
			mobilephone_del.setVisibility(View.GONE);
			break;
		case R.id.tellphone_del:
			et_tellphone.getText().clear();
			tellphone_del.setVisibility(View.GONE);
			break;
		case R.id.email_del:
			et_email.getText().clear();
			email_del.setVisibility(View.GONE);
			break;
		case R.id.qq_del:
			et_qq.getText().clear();
			qq_del.setVisibility(View.GONE);
			break;
		case R.id.address_del:
			et_address.getText().clear();
			address_del.setVisibility(View.GONE);
			break;
		case R.id.et_name:
			name_del.setVisibility(View.VISIBLE);
			break;
		case R.id.et_company:
			company_del.setVisibility(View.VISIBLE);
			break;
		case R.id.et_post:
			post_del.setVisibility(View.VISIBLE);
			break;
		case R.id.et_mobialphone:
			mobilephone_del.setVisibility(View.VISIBLE);
			break;
		case R.id.et_tellphone:
			tellphone_del.setVisibility(View.VISIBLE);
			break;
		case R.id.et_email:
			email_del.setVisibility(View.VISIBLE);
			break;
		case R.id.et_qq:
			qq_del.setVisibility(View.VISIBLE);
			break;
		case R.id.et_address:
			address_del.setVisibility(View.VISIBLE);
			break;
		case R.id.bt_delete:
			AlertDialog.Builder delete = new Builder(ConstactsEditActivity.this);
			delete.setMessage("你确定要删除联系人吗?");
			delete.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								delete();
								Toast.makeText(ConstactsEditActivity.this,
										"删除成功", Toast.LENGTH_SHORT).show();
								finish();
							} catch (Exception e) {
								Toast.makeText(ConstactsEditActivity.this,
										"删除失败", Toast.LENGTH_SHORT).show();
							}

						}
					});
			delete.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}

					});
			delete.show();
			break;
		case R.id.tv_choose:
			if (status) {
				AlertDialog.Builder update = new Builder(
						ConstactsEditActivity.this);
				update.setMessage("你确定要修改联系人吗?");
				update.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									update(constactName,
											et_name.getText().toString().trim(),
											et_company.getText().toString()
													.trim(),
											et_post.getText().toString().trim(),
											et_mobilephone.getText().toString()
													.trim(), et_tellphone
													.getText().toString()
													.trim(), et_email.getText()
													.toString().trim(), et_qq
													.getText().toString()
													.trim(), et_address
													.getText().toString()
													.trim(), et_remark
													.getText().toString()
													.trim());
									 Toast.makeText(ConstactsEditActivity.this,
									 "更新联系人成功", Toast.LENGTH_LONG)
									 .show();
									finish();
								} catch (Exception e) {
									 Toast.makeText(ConstactsEditActivity.this,
									 "更新联系人失", Toast.LENGTH_LONG)
									 .show();
								}

							}
						});
				update.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						});
				update.show();
			} else {
				if (et_name.getText().toString().trim() == null) {
					Toast.makeText(ConstactsEditActivity.this, "姓名不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				try {
					insert(et_name.getText().toString().trim(), et_company
							.getText().toString().trim(), et_post.getText()
							.toString().trim(), et_mobilephone.getText()
							.toString().trim(), et_tellphone.getText()
							.toString().trim(), et_email.getText().toString()
							.trim(), et_qq.getText().toString().trim(),
							et_address.getText().toString().trim(), et_remark
									.getText().toString().trim());
					// Toast.makeText(ConstactsEditActivity.this, "添加联系人成�?,
					// Toast.LENGTH_SHORT).show();
					finish();
				} catch (Exception e) {
					// Toast.makeText(ConstactsEditActivity.this, "添加联系人失�?,
					// Toast.LENGTH_SHORT).show();
				}

			}

			break;
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}
	}

	/**
	 * 添加联系�?
	 */
	private void insert(String name, String company, String post,
			String mobilephone, String tellphone, String email, String qq,
			String address, String remark) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		// 在名片表插入�?��新名�?
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts._ID, 0)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.withValue(ContactsContract.RawContacts.AGGREGATION_MODE,
						ContactsContract.RawContacts.AGGREGATION_MODE_DISABLED)
				.build());

		// 姓名
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						name).build());

		// 公司
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.Organization.COMPANY,
						company)
				.withValue(ContactsContract.CommonDataKinds.Organization.TYPE,
						ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
				.build());
		// 职位
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Organization.TITLE,
						post)
				.withValue(ContactsContract.CommonDataKinds.Organization.TITLE,
						ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
				.build());

		// 手机
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
						mobilephone)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
						Phone.TYPE_MOBILE).build());
		// 电话
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
						tellphone)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
						Phone.TYPE_HOME).build());

		// 邮箱
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
				.withValue(ContactsContract.CommonDataKinds.Email.TYPE,
						Email.TYPE_WORK).build());

		// QQ
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Im.DATA1, qq)
				.withValue(ContactsContract.CommonDataKinds.Im.PROTOCOL,
						ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ)
				.build());

		// 地址
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
						address)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
						ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK)
				.build());

		// 备注
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Note.NOTE, remark)
				.build());

		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (Exception e) {

		}
	}

	/**
	 * 更新联系�?
	 */
	@SuppressWarnings("unused")
	private void update(String oldname, String name, String company,
			String post, String mobilephone, String tellphone, String email,
			String qq, String address, String note) {
		Cursor cursor = getContentResolver().query(Data.CONTENT_URI,
				new String[] { Data.RAW_CONTACT_ID },
				ContactsContract.Contacts.DISPLAY_NAME + "=?",
				new String[] { oldname }, null);
		cursor.moveToFirst();
		String id = cursor
				.getString(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
		cursor.close();
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		// 更新姓名
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + " = ?",
						new String[] { String.valueOf(id),
								StructuredName.CONTENT_ITEM_TYPE })
				.withValue(StructuredName.DISPLAY_NAME, name).build());
		// 更新公司
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + " = ?",
						new String[] { String.valueOf(id),
								Organization.CONTENT_ITEM_TYPE })
				.withValue(Organization.COMPANY, company).build());
		// 更新职位
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + " = ?",
						new String[] { String.valueOf(id),
								Organization.CONTENT_ITEM_TYPE })
				.withValue(Organization.TITLE, post).build());
		// 更新号码1(手机)
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + " = ?"
								+ " AND " + Phone.TYPE + "=?",
						new String[] { String.valueOf(id),
								Phone.CONTENT_ITEM_TYPE,
								String.valueOf(Phone.TYPE_MOBILE) })
				.withValue(Phone.NUMBER, mobilephone).build());
		// 更新号码2(家庭电话)
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + " = ?"
								+ " AND " + Phone.TYPE + "=?",
						new String[] { String.valueOf(id),
								Phone.CONTENT_ITEM_TYPE,
								String.valueOf(Phone.TYPE_HOME) })
				.withValue(Phone.NUMBER, tellphone).build());

		// 更新email
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + " = ?"
								+ " AND " + Email.TYPE + "=?",
						new String[] { String.valueOf(id),
								Email.CONTENT_ITEM_TYPE,
								String.valueOf(Email.TYPE_WORK) })
				.withValue(Email.DATA, email).build());
		// 更新IM
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + " = ?",
						new String[] { String.valueOf(id), Im.CONTENT_ITEM_TYPE })
				.withValue(Im.DATA, qq).build());
		// 更新地址
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + " = ?",
						new String[] {
								String.valueOf(id),
								ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE })
				.withValue(
						ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
						address).build());

		// 更新备注
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(
						Data.RAW_CONTACT_ID + "=?" + " AND "
								+ ContactsContract.Data.MIMETYPE + " = ?",
						new String[] { String.valueOf(id),
								Note.CONTENT_ITEM_TYPE })
				.withValue(Note.NOTE, note).build());

		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (Exception e) {

		}
	}

	/**
	 * 删除联系�?
	 */
	private void delete() {
		Cursor cursor = getContentResolver().query(Data.CONTENT_URI,
				new String[] { Data.RAW_CONTACT_ID },
				ContactsContract.Contacts.DISPLAY_NAME + "=?",
				new String[] { constactName }, null);
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		if (cursor.moveToFirst()) {
			do {
				long Id = cursor.getLong(cursor
						.getColumnIndex(Data.RAW_CONTACT_ID));
				ops.add(ContentProviderOperation
						.newDelete(
								ContentUris.withAppendedId(
										RawContacts.CONTENT_URI, Id)).build());
				try {
					getContentResolver().applyBatch(ContactsContract.AUTHORITY,
							ops);
				} catch (Exception e) {

				}
			} while (cursor.moveToNext());
			cursor.close();
		}
	}
}
