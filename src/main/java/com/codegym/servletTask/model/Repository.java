package com.codegym.servletTask.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Repository {

    private static final Map<Integer, Question> QUESTIONS = new HashMap<>();
    private static final Map<Integer, Answer> ANSWERS = new HashMap<>();

    public Repository() {
        Question q1 = new Question(1, "You lost your memory. Accept UFO challenge?", Question.Type.USUAL);
        Question q2 = new Question(2, "You accepted the challenge. Are you going up to the captain's bridge?", Question.Type.USUAL);
        Question q3 = new Question(3, "You rejected the challenge. Defeat.", Question.Type.LOST);
        Question q4 = new Question(4, "You've gone up to the bridge. Who are you?", Question.Type.USUAL);
        Question q5 = new Question(5, "You didn't attend the negotiations. Defeat.", Question.Type.LOST);
        Question q6 = new Question(6, "You've been returned home. Victory.", Question.Type.WON);
        Question q7 = new Question(7, "Your lie has been revealed. Defeat.", Question.Type.LOST);

        Answer a1 = new Answer(1, "Accept the challenge.", q1, q2);
        Answer a2 = new Answer(2, "Reject the challenge.", q1, q3);
        Answer a3 = new Answer(3, "Go up to the bridge.", q2, q4);
        Answer a4 = new Answer(4, "Refuse to go up to the bridge.", q2, q5);
        Answer a5 = new Answer(5, "Tell the truth about yourself.", q4, q6);
        Answer a6 = new Answer(6, "Lie about yourself", q4, q7);

        QUESTIONS.put(q1.getId(), q1);
        QUESTIONS.put(q2.getId(), q2);
        QUESTIONS.put(q3.getId(), q3);
        QUESTIONS.put(q4.getId(), q4);
        QUESTIONS.put(q5.getId(), q5);
        QUESTIONS.put(q6.getId(), q6);
        QUESTIONS.put(q7.getId(), q7);

        ANSWERS.put(a1.getId(), a1);
        ANSWERS.put(a2.getId(), a2);
        ANSWERS.put(a3.getId(), a3);
        ANSWERS.put(a4.getId(), a4);
        ANSWERS.put(a5.getId(), a5);
        ANSWERS.put(a6.getId(), a6);
    }

    public Question getQuestionById(Integer id) {
        return QUESTIONS.get(id);
    }

    public Answer getAnswerById(Integer id) {
        return ANSWERS.get(id);
    }

    public Collection<Answer> getAnswerByFromQuestionId(Integer id) {
        return ANSWERS.values().stream()
                .filter(a -> a.getFrom().getId().equals(id))
                .collect(Collectors.toSet());
    }
}
