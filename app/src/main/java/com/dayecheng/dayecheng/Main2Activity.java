package com.dayecheng.dayecheng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1, btn2;// 加密，解密
    private EditText et1, et2, et3;// 需加密的内容，加密后的内容，解密后的内容

    /* 密钥内容 base64 code */
    private static String PUCLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6B6Gvo3JAhEsaIqgwW4Cg2XmqcJdDnd8LUajmN+ipvq9No2DLTvhFqIPWw8xtJNnhoCF2RjvcZmhpGLk4HKQL2Ua8uporWC8Qr1BIVAAbLhDVEmgld9jiWDaMxYHSGNLH5dQGQ3lD/GAEeVaK7QPNobo4CxXf0hUcKkbmXa4eJ4GMhKJZMu3iwEnWjMVbHBs6FS17o/J9XGAS3ia8ruglL8hTQDIs4VjoG/6TQcbmGyUa52oFvfh5VLTB1Tz0ZVJwWQVDmYjXIWTFIjoQzJKWL6pg8PObvn7Yu/NKeHRjqm6qSykIKnNbnqvbBJl/j36jYUpK0M7mGFUUjTsxVEBVwIDAQAB";
    private static String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDoHoa+jckCESxoiqDBbgKDZeapwl0Od3wtRqOY36Km+r02jYMtO+EWog9bDzG0k2eGgIXZGO9xmaGkYuTgcpAvZRry6mitYLxCvUEhUABsuENUSaCV32OJYNozFgdIY0sfl1AZDeUP8YAR5VortA82hujgLFd/SFRwqRuZdrh4ngYyEolky7eLASdaMxVscGzoVLXuj8n1cYBLeJryu6CUvyFNAMizhWOgb/pNBxuYbJRrnagW9+HlUtMHVPPRlUnBZBUOZiNchZMUiOhDMkpYvqmDw85u+fti780p4dGOqbqpLKQgqc1ueq9sEmX+PfqNhSkrQzuYYVRSNOzFUQFXAgMBAAECggEAdfo+BlBOYEyWQ7RiLK5bfKmoaZQudBHq/aAvup3AMBq1XSrkB3Ot3jEUmPXKD7v0HHrqjQONgDHT5JeXOTVBb7V10w2tkqHeThWfKu1cqeWl4wthIYvfLY4amWiS3woIvs/LmFBlooreyznFcP0d1lsPthETj9AViwWIz6F19Wd0XgjEhM/U2rnTbY5CwZWxXEmSMql02Yi5d617r/5nrDF+4co5ZsNhKPtZWapTgr0FtU64AgZtO3/BRt3fbpU5h4CMz5ri5wODIvZ0/o1z2bYRCt2p8rrk8Qf05H5dBCJd75rodvVPag5kEw2Y5TFq9uofG86k7bLTWhpXiyHKcQKBgQD6kXUC6VATW6H1se6dgr/uEbQjgk8duFtzHgrGouxZ5SETkY9WFX3qRf2TYitg3T84zfwvKTLkyKtKicUMdqwKqdt9eQMwE8E87AkqNkOTelbajO0feM92/e3gtm8p7nXczFHFfxtt2Zvjt1ae/DO9S50cA1M7CJ5VQnqSSFwWtQKBgQDtJq+NDITODPtzseBnAZZnbcqfZNyotgq7HCEuJHqSB2QNLzEcqAuAc6qYT614QoM7GqNKmHlLHn3HvIMNLdbJCfXoFDp1uKXDUVOsvDuIxz4OQPtSMi6zVmINjl4nahoue4lECHMiLiI1+9NnEq8itafBXQO/eqjXtIUsYT+TWwKBgQCbFbp5odSZFqcIvid1P/7xJ+356GZ1e6zGdHqw9Rgaj7HIPdicrFKx1L4dIa69llLSItQKkJJafkVeriTTEQRs2TLr1yF8+U0qGuGVSd7LgfYN8Rm21/x4prQ72E63gkta7O9TsNdWR1CwQcKQRoLzj8BfHUO/jxcIqEvbDIRK3QKBgGESdsVbnHjjY4IGC37ebXxEuq3oEVKVwWq5pvNGR03C7Ldg28+JEbwwnGHgK3eQcjsdOj3sj6mp/35ksZgRbVu5Ugj/TralMGT0wzVvj0b7l3EqNDV/CRKrh1NZNT0Y90KTmqpYcxhIngRO4kfMpBenF9XUhxY31SkGYYS7AdUFAoGAd8BR5Yly49hP5hp5d1NkY+POmu7NZwXzfQTKeKE10pwAymHmUc0ENcCzqbdr4Wy5cPJeiWxbcNxq5FmS0rqbr7nxrMONS/E45H0IwBYzbHR/kimUTCJVyDHuMVoPRUEiSz5tra2QsLS785SxwljZFUT8Xx5mDrVy/4Yk/kpJtsY=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
    }



    private static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    byte[] encrypted;
    String key = "20171117";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 加密
            case R.id.btn1:
                String content = et1.getText().toString();

                System.out.println("加密前：" + content);
//                encrypted = DES_CBC_Encrypt(content.getBytes(), key.getBytes());
//                System.out.println("加密后：" + byteToHexString(encrypted));
                et2.setText(byteToHexString(encrypted));


                // 解密
            case R.id.btn2:
//                byte[] decrypted = DES_CBC_Decrypt(encrypted, key.getBytes());
//                System.out.println("解密后：" + new String(decrypted));
//                et3.setText("解密后：" + new String(decrypted));
                break;
            default:
                break;
        }
    }
}
