package com.shishkindenis.locationtracker_parent.examples;

/*public class PhoneActivityOld {
    public class PhoneAuthActivity extends MvpAppCompatActivity implements PhoneAuthView {

        @InjectPresenter
        PhoneAuthPresenter phoneAuthPresenter;

        private ActivityPhoneAuthBinding binding;
        private FirebaseAuth auth;
        private String mVerificationId;
        private PhoneAuthProvider.ForceResendingToken mResendToken;
        private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);

            auth = FirebaseAuth.getInstance();

            binding.btnRequestCode.setOnClickListener(v -> {
                binding.pbPhoneAuth.setVisibility(View.VISIBLE);
                if (validatePhoneNumber()) {
                    startPhoneNumberVerification(binding.etPhoneNumber.getText().toString());
                }
                binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
            });

            binding.btnVerifyCode.setOnClickListener(v -> {
                binding.pbPhoneAuth.setVisibility(View.VISIBLE);
                if (validateCode()) {
                    verifyPhoneNumberWithCode(
                            mVerificationId, binding.etVerificationCode.getText().toString());
                }
                binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
            });


            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    signInWithPhoneAuthCredential(credential);
                }
                @Override
                public void onVerificationFailed(FirebaseException e) {
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        binding.etPhoneNumber.setError(getString(R.string.invalid_phone_number));
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        showToast(R.string.quota_exceeded);
                    }
                }
                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    mVerificationId = verificationId;
                    mResendToken = token;
                    binding.btnVerifyCode.setEnabled(true);
                }
            };
        }

        private void startPhoneNumberVerification(String phoneNumber) {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber(phoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(mCallbacks)
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }

        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            showToast(R.string.authentication_successful);
//                        getViewState().showToast(R.string.authentication_successful);
                            goToAnotherActivity(CalendarActivity.class);
                        } else {
                            showToast((R.string.authentication_failed));
//                        getViewState().showToast((R.string.authentication_failed));
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                binding.etVerificationCode.setError(getString(R.string.invalid_code));
                            }
                        }
                    });
        }

        private boolean validatePhoneNumber() {
            if (binding.etPhoneNumber.getText().toString().isEmpty()) {
                binding.etPhoneNumber.setError(getString(R.string.invalid_phone_number));
                showToast(R.string.invalid_phone_number);
                return false;
            }
            return true;
        }

        private boolean validateCode() {
            if (binding.etVerificationCode.getText().toString().isEmpty()) {
                binding.etVerificationCode.setError(getString(R.string.cannot_be_empty));
                showToast(R.string.cannot_be_empty);
                return false;
            }
            return true;
        }


        private void verifyPhoneNumberWithCode(String verificationId, String code) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
        }


        @Override
        public void goToAnotherActivity(Class activity){
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        }

        @Override
        public void showToast(int toastMessage){
            Toast.makeText(getApplicationContext(), toastMessage,
                    Toast.LENGTH_LONG).show();
        }

        public void enableVerifyButton(){
            binding.btnVerifyCode.setEnabled(true);
        }

    }
}
/*


 */
