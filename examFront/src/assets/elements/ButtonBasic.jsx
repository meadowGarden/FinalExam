const ButtonBasic = ({ clickHandle, text }) => {
  return (
    <button onClick={clickHandle} className="basicButton">
      {text}
    </button>
  );
};

export default ButtonBasic;
