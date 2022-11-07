package org.cybercrowd.mvp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BallotRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public BallotRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andProposalNoIsNull() {
            addCriterion("proposal_no is null");
            return (Criteria) this;
        }

        public Criteria andProposalNoIsNotNull() {
            addCriterion("proposal_no is not null");
            return (Criteria) this;
        }

        public Criteria andProposalNoEqualTo(String value) {
            addCriterion("proposal_no =", value, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoNotEqualTo(String value) {
            addCriterion("proposal_no <>", value, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoGreaterThan(String value) {
            addCriterion("proposal_no >", value, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoGreaterThanOrEqualTo(String value) {
            addCriterion("proposal_no >=", value, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoLessThan(String value) {
            addCriterion("proposal_no <", value, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoLessThanOrEqualTo(String value) {
            addCriterion("proposal_no <=", value, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoLike(String value) {
            addCriterion("proposal_no like", value, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoNotLike(String value) {
            addCriterion("proposal_no not like", value, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoIn(List<String> values) {
            addCriterion("proposal_no in", values, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoNotIn(List<String> values) {
            addCriterion("proposal_no not in", values, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoBetween(String value1, String value2) {
            addCriterion("proposal_no between", value1, value2, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andProposalNoNotBetween(String value1, String value2) {
            addCriterion("proposal_no not between", value1, value2, "proposalNo");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdIsNull() {
            addCriterion("ballot_user_id is null");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdIsNotNull() {
            addCriterion("ballot_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdEqualTo(String value) {
            addCriterion("ballot_user_id =", value, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdNotEqualTo(String value) {
            addCriterion("ballot_user_id <>", value, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdGreaterThan(String value) {
            addCriterion("ballot_user_id >", value, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("ballot_user_id >=", value, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdLessThan(String value) {
            addCriterion("ballot_user_id <", value, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdLessThanOrEqualTo(String value) {
            addCriterion("ballot_user_id <=", value, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdLike(String value) {
            addCriterion("ballot_user_id like", value, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdNotLike(String value) {
            addCriterion("ballot_user_id not like", value, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdIn(List<String> values) {
            addCriterion("ballot_user_id in", values, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdNotIn(List<String> values) {
            addCriterion("ballot_user_id not in", values, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdBetween(String value1, String value2) {
            addCriterion("ballot_user_id between", value1, value2, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotUserIdNotBetween(String value1, String value2) {
            addCriterion("ballot_user_id not between", value1, value2, "ballotUserId");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeIsNull() {
            addCriterion("ballot_option_code is null");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeIsNotNull() {
            addCriterion("ballot_option_code is not null");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeEqualTo(String value) {
            addCriterion("ballot_option_code =", value, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeNotEqualTo(String value) {
            addCriterion("ballot_option_code <>", value, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeGreaterThan(String value) {
            addCriterion("ballot_option_code >", value, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ballot_option_code >=", value, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeLessThan(String value) {
            addCriterion("ballot_option_code <", value, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeLessThanOrEqualTo(String value) {
            addCriterion("ballot_option_code <=", value, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeLike(String value) {
            addCriterion("ballot_option_code like", value, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeNotLike(String value) {
            addCriterion("ballot_option_code not like", value, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeIn(List<String> values) {
            addCriterion("ballot_option_code in", values, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeNotIn(List<String> values) {
            addCriterion("ballot_option_code not in", values, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeBetween(String value1, String value2) {
            addCriterion("ballot_option_code between", value1, value2, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andBallotOptionCodeNotBetween(String value1, String value2) {
            addCriterion("ballot_option_code not between", value1, value2, "ballotOptionCode");
            return (Criteria) this;
        }

        public Criteria andVoteIsNull() {
            addCriterion("vote is null");
            return (Criteria) this;
        }

        public Criteria andVoteIsNotNull() {
            addCriterion("vote is not null");
            return (Criteria) this;
        }

        public Criteria andVoteEqualTo(Long value) {
            addCriterion("vote =", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteNotEqualTo(Long value) {
            addCriterion("vote <>", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteGreaterThan(Long value) {
            addCriterion("vote >", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteGreaterThanOrEqualTo(Long value) {
            addCriterion("vote >=", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteLessThan(Long value) {
            addCriterion("vote <", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteLessThanOrEqualTo(Long value) {
            addCriterion("vote <=", value, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteIn(List<Long> values) {
            addCriterion("vote in", values, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteNotIn(List<Long> values) {
            addCriterion("vote not in", values, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteBetween(Long value1, Long value2) {
            addCriterion("vote between", value1, value2, "vote");
            return (Criteria) this;
        }

        public Criteria andVoteNotBetween(Long value1, Long value2) {
            addCriterion("vote not between", value1, value2, "vote");
            return (Criteria) this;
        }

        public Criteria andBallotTimeIsNull() {
            addCriterion("ballot_time is null");
            return (Criteria) this;
        }

        public Criteria andBallotTimeIsNotNull() {
            addCriterion("ballot_time is not null");
            return (Criteria) this;
        }

        public Criteria andBallotTimeEqualTo(Date value) {
            addCriterion("ballot_time =", value, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andBallotTimeNotEqualTo(Date value) {
            addCriterion("ballot_time <>", value, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andBallotTimeGreaterThan(Date value) {
            addCriterion("ballot_time >", value, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andBallotTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ballot_time >=", value, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andBallotTimeLessThan(Date value) {
            addCriterion("ballot_time <", value, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andBallotTimeLessThanOrEqualTo(Date value) {
            addCriterion("ballot_time <=", value, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andBallotTimeIn(List<Date> values) {
            addCriterion("ballot_time in", values, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andBallotTimeNotIn(List<Date> values) {
            addCriterion("ballot_time not in", values, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andBallotTimeBetween(Date value1, Date value2) {
            addCriterion("ballot_time between", value1, value2, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andBallotTimeNotBetween(Date value1, Date value2) {
            addCriterion("ballot_time not between", value1, value2, "ballotTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}