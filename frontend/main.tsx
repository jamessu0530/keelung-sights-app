import { createRoot } from 'react-dom/client'
import { useState } from 'react'
import './styles.css'
import TextPressure from './TextPressure'

interface SightData {
  [key: string]: any
}

function App(): JSX.Element {
  const [zone, setZone] = useState<string>('中正')
  const [text, setText] = useState<string>('')
  const [loading, setLoading] = useState<boolean>(false)
  const [error, setError] = useState<string>('')

  const fetchData = async (): Promise<void> => {
    setLoading(true)
    setError('')
    try {
      const res: Response = await fetch(`/api/sights?zone=${encodeURIComponent(zone)}`)
      if (!res.ok) {
        const msg: string = await res.text()
        throw new Error(msg || `HTTP ${res.status}`)
      }
      const data: SightData = await res.json()
      setText(JSON.stringify(data, null, 2))
    } catch (e: any) {
      setText('')
      setError(e.message || 'An error occurred')
    } finally {
      setLoading(false)
    }
  }

 
  return (
    <div>
      <div className="title-container">
        <div className="title-wrapper">
          <TextPressure
            text="Keelung Sights"
            flex={true}
            alpha={false}
            stroke={false}
            width={true}
            weight={true}
            italic={true}
            textColor="#ffffff"
            strokeColor="#90ee90"
            minFontSize={24}
          />
        </div>
      </div>
      <div>
        {/* 輸入框：值由 React state zone 控制，onChange 更新 state */}
        <input
          value={zone}
          onChange={(e) => setZone(e.target.value)}
        />
        {/* 輸入完成，下面轉到爬後端 API */}
        <button onClick={fetchData} disabled={loading}>
          {/*如果 loading 為 true，按鈕就會被禁用 */}
          {loading ? 'Loading...' : 'Fetch'}
          {/* 按鈕：用三元運算子顯示文字，載入時顯示 Loading，否則顯示 Fetch */}
        </button>
      </div>
      {error && <pre>{error}</pre>}
      {/*error && ...：只有 error 有值時才會渲染 <pre>{error}</pre>*/}
      <pre>{text}</pre>
      {/*{text}：顯示從 API 回來的資料（可能是 JSON 字串）*/}
    </div>
  )
}

createRoot(document.getElementById('root')!).render(<App />)
