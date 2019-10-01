package com.example.macbook.perkupapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class ChatActivity extends AppCompatActivity {

    private static final int USER = 1;
    private static final int BOT = 2;

    private String uuid = UUID.randomUUID().toString();
    private LinearLayout chatLayout;
    private EditText inputMessage;

    private AIRequest aiRequest;
    private AIDataService aiDataService;
    private AIServiceContext customAIServiceContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);

        final ScrollView scrollView = findViewById(R.id.chatScrollView);
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        };
        scrollView.post(runnable);

        chatLayout = findViewById(R.id.chatLayout);

        final ImageView sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(view);
            }
        });

        inputMessage = findViewById(R.id.queryEditText);
        inputMessage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            sendMessage(sendBtn);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        initChatbot();


    }
    private void showTextView(String msg, int type) {
        FrameLayout layout;
        switch (type) {
            case USER:
                layout = getUserLayout();
                break;
            case BOT:
                layout = getBotLayout();
                break;
            default:
                layout = getBotLayout();
                break;
        }
        layout.setFocusableInTouchMode(true);
        chatLayout.addView(layout);
        TextView textView = layout.findViewById(R.id.chatMsg);
        textView.setText(msg);

        layout.requestFocus();
        inputMessage.requestFocus();
    }


    private void initChatbot() {
        final AIConfiguration config = new AIConfiguration("5d24aba8559f41bfa4b976c9a27a9e93",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiDataService = new AIDataService(this, config);
        customAIServiceContext = AIServiceContextBuilder.buildFromSessionId(uuid);
        aiRequest = new AIRequest();
    }



    private void sendMessage(View view) {
        String msg = inputMessage.getText().toString();
        if (!msg.trim().isEmpty()) {
            showTextView(msg, USER);
            inputMessage.setText("");
            aiRequest.setQuery(msg);
            RequestTask requestTask = new RequestTask(ChatActivity.this, aiDataService, customAIServiceContext);
            requestTask.execute(aiRequest);

        } else {

            Toast.makeText(ChatActivity.this, "Enter a message.", Toast.LENGTH_LONG).show();
        }
    }

    public void callback(AIResponse aiResponse) {
        String botReply = aiResponse.getResult().getFulfillment().getSpeech();
        showTextView(botReply, BOT);

    }




    FrameLayout getUserLayout() {
        LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
        return (FrameLayout) inflater.inflate(R.layout.my_msg, null);
    }

    FrameLayout getBotLayout() {
        LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
        return (FrameLayout) inflater.inflate(R.layout.bot_msg, null);
    }
}
