package ra.service;


import ra.model.Feedback;
import ra.util.DataBase;

import java.util.ArrayList;
import java.util.List;

public class FeedbackService implements IGenericService<Feedback, Integer> {
    private List<Feedback> feedbacks;

    private DataBase<Feedback> feedbackData = new DataBase();

    public FeedbackService(List<Feedback> feedbacks) {
        List<Feedback> feedbackList = feedbackData.readFromFile(DataBase.FEEDBACK_PATH);
        if (feedbackList == null) {
            feedbackList = new ArrayList<>();
        }
        this.feedbacks = feedbackList;
    }

    @Override
    public List<Feedback> findAll() {
        return feedbacks;
    }

    @Override
    public void save(Feedback feedback) {
        if (findById((feedback.getFeedbackId())) == null) {
            feedbacks.add(feedback);
        } else {
            feedbacks.set(feedbacks.indexOf(findById(feedback.getFeedbackId())), feedback);
        }
        feedbackData.writeToFile(feedbacks, DataBase.FEEDBACK_PATH);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Feedback findById(Integer id) {
        for (Feedback feedback : feedbacks) {
            if (feedback.getFeedbackId() == id) {
                return feedback;
            }
        }
        return null;
    }
}

