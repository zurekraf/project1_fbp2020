package pl.ske.project1.entity;

//TO NIE JEST ENCJA< TYLKO o takie coś co ma nam zwracać i tyle

public class ExampleModel {
    private final long id;
    private final String data;

    public ExampleModel(long id, String data) {
        this.id = id;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
