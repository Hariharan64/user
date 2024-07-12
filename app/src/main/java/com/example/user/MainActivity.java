package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;





        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAuthor, editTextDescription;
    private Button buttonSubmit, buttonViewBooks;
    private ListView listViewBooks;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Book> bookList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





            // Initialize Firebase Database
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Books");

            // Initialize UI elements
            editTextTitle = findViewById(R.id.editTextTitle);
            editTextAuthor = findViewById(R.id.editTextAuthor);
            editTextDescription = findViewById(R.id.editTextDescription);
            buttonSubmit = findViewById(R.id.buttonSubmit);
            buttonViewBooks = findViewById(R.id.buttonViewBooks);
            listViewBooks = findViewById(R.id.listViewBooks);

            bookList = new ArrayList<>();

            buttonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitBookDetails();
                }
            });

            buttonViewBooks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewBookDetails();
                    Intent intent=new Intent(MainActivity.this,demoActivity.class);
                    startActivity(intent);
                }
            });
        }

        private void submitBookDetails() {
            String title = editTextTitle.getText().toString().trim();
            String author = editTextAuthor.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(description)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Generate a unique ID for each book
            String bookId = databaseReference.push().getKey();

            // Create a Book object
            Book book = new Book(bookId, title, author, description);

            // Save the book to the database
            if (bookId != null) {
                databaseReference.child(bookId).setValue(book);
                Toast.makeText(this, "Book details submitted", Toast.LENGTH_SHORT).show();
            }
        }

        private void viewBookDetails() {
            listViewBooks.setVisibility(View.VISIBLE);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    bookList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Book book = postSnapshot.getValue(Book.class);
                        bookList.add(book);
                    }

                    BookListAdapter adapter = new BookListAdapter(MainActivity.this, bookList);
                    listViewBooks.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Define a Book class
        public static class Book {
            public String bookId;
            public String title;
            public String author;
            public String description;

            public Book() {
                // Default constructor required for calls to DataSnapshot.getValue(Book.class)
            }

            public Book(String bookId, String title, String author, String description) {
                this.bookId = bookId;
                this.title = title;
                this.author = author;
                this.description = description;
            }
        }
    }
